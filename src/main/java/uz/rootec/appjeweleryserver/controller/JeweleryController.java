package uz.rootec.appjeweleryserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.rootec.appjeweleryserver.entity.Characteristic;
import uz.rootec.appjeweleryserver.entity.Diamond;
import uz.rootec.appjeweleryserver.entity.Jewelery;
import uz.rootec.appjeweleryserver.entity.User;
import uz.rootec.appjeweleryserver.payload.ApiResponse;
import uz.rootec.appjeweleryserver.payload.ReqCharacteristic;
import uz.rootec.appjeweleryserver.payload.ReqDiamond;
import uz.rootec.appjeweleryserver.payload.ReqJewelery;
import uz.rootec.appjeweleryserver.projection.CustomDiamond;
import uz.rootec.appjeweleryserver.projection.CustomJewelery;
import uz.rootec.appjeweleryserver.repository.AttachmentRepository;
import uz.rootec.appjeweleryserver.repository.CharacteristicRepository;
import uz.rootec.appjeweleryserver.repository.DiamondRepository;
import uz.rootec.appjeweleryserver.repository.JeweleryRepository;
import uz.rootec.appjeweleryserver.security.CurrentUser;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Sherlock on 14.07.2021.
 */
@RestController
@RequestMapping("api/jewelery")
public class JeweleryController {
    @Autowired
    private DiamondRepository diamondRepository;

    @Autowired
    private CharacteristicRepository characteristicRepository;

//    @Autowired
//    private ExpertRepository expertRepository;

    @Autowired
    private JeweleryRepository jeweleryRepository;

    @Autowired
    private UserController userController;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN')")
    @GetMapping
    public HttpEntity<?> getAll(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(new ApiResponse(true, "", jeweleryRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size))));
    }

    @GetMapping("/byExpert/{id}")
    public HttpEntity<?> getAllByExpert(@PathVariable UUID id, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(new ApiResponse(true, "", jeweleryRepository.findAllByExpertIdOrderByCreatedAtDesc(id, PageRequest.of(page, size))));
    }

    @PostMapping("/saveOrUpdate")
    public HttpEntity<?> saveOrUpdate(@RequestBody ReqJewelery reqJewelery, @CurrentUser User currentUser){
        try {
            Jewelery jewelery;

            if (reqJewelery.getId() == null){
                jewelery = new Jewelery();

                String yourValue = "";

                while (yourValue.length() == 0){
                    Random rand = new Random();
                    yourValue = String.format((Locale)null, //don't want any thousand separators
                            "1%03d%04d%04d%04d",
                            rand.nextInt(1000),
                            rand.nextInt(10000),
                            rand.nextInt(10000),
                            rand.nextInt(10000));
                    if (!jeweleryRepository.findAllBySerial(yourValue).isEmpty()){
                        yourValue = "";
                    }
                }

                jewelery.setSerial(yourValue);
            }
            else
                jewelery = jeweleryRepository.getOne(reqJewelery.getId());

            jewelery.setDate(reqJewelery.getDate());
            jewelery.setExpert(currentUser);
            jewelery.setHallMark(reqJewelery.getHallMark());
            jewelery.setMetal(reqJewelery.getMetal());
            jewelery.setName(reqJewelery.getName());
            jewelery.setTotalWeight(reqJewelery.getTotalWeight());

            if (reqJewelery.getPhoto() != null)
                jewelery.setPhoto(attachmentRepository.getOne(reqJewelery.getPhoto()));

            jeweleryRepository.save(jewelery);

            List<ReqDiamond> diamonds = reqJewelery.getDiamonds();

            for (ReqDiamond diamond : diamonds) {
                List<ReqCharacteristic> characteristics = diamond.getCharacteristics();
                Diamond newDiamond;
                if (diamond.getId() == null){
                    newDiamond = new Diamond(
                            diamond.getDiamond(),
                            jewelery
                    );
                } else {
                    newDiamond = diamondRepository.findById(diamond.getId()).orElseThrow(() -> new Exception("Diamond Not Found"));
                    newDiamond.setDiamond(diamond.getDiamond());
                    newDiamond.setJewelery(jewelery);
                }

                diamondRepository.save(newDiamond);

                for (ReqCharacteristic characteristic : characteristics) {
                    if (characteristic.getId() == null){
                        Characteristic newCharacteristic = new Characteristic(
                                characteristic.getName(),
                                characteristic.getValueOne(),
                                characteristic.getValueTwo(),
                                newDiamond
                        );
                        characteristicRepository.save(newCharacteristic);
                    } else {
                        Characteristic characteristic_not_found = characteristicRepository.findById(characteristic.getId()).orElseThrow(() -> new Exception("Characteristic not found"));
                        characteristic_not_found.setDiamond(newDiamond);
                        characteristic_not_found.setName(characteristic.getName());
                        characteristic_not_found.setValueOne(characteristic.getValueOne());
                        characteristic_not_found.setValueTwo(characteristic.getValueTwo());

                        characteristicRepository.save(characteristic_not_found);
                    }
                }

            }

            if (reqJewelery.getId() != null) {
                return ResponseEntity.ok(new ApiResponse(true, "O'zgartirildi"));
            } else {
                return ResponseEntity.ok(new ApiResponse(true, "Qo'shildi"));
            }

        } catch (Exception e){
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage()));
        }
    }

    @GetMapping("/check/{serial}")
    public HttpEntity<?> checkJewelery(@PathVariable String serial){
        try {
            CustomJewelery bySerial = jeweleryRepository.findBySerial(serial);
            return ResponseEntity.ok(new ApiResponse(true, "success", bySerial));
        } catch (Exception e){
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage()));
        }


    }
}
