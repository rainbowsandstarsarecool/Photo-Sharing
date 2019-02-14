package net.ukr.just_void.controllers;

import net.ukr.just_void.exception.UploadedFileInvalidException;
import net.ukr.just_void.exception.UploadedFileNotFoundException;
import net.ukr.just_void.model.FileEntity;
import net.ukr.just_void.service.FileEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Controller
public class PhotoSharingController {
    @Autowired
    private FileEntityService fileEntityService;

    @RequestMapping("/")
    public String onIndex() {
        return "index";
    }

    @RequestMapping("/list")
    public ModelAndView onList() {
        Set<String> fileEntityHashHexStringSet = new HashSet<>();
        for (Integer i : fileEntityService.getFileEntityHashSet()) {
            fileEntityHashHexStringSet.add(Integer.toHexString(i));
        }
        return new ModelAndView("list", "photos_key_set",
                fileEntityService.isEmpty() ? null : fileEntityHashHexStringSet);
    }

    @RequestMapping(value = "/add_file", method = RequestMethod.POST)
    public String onAddPhoto(Model model, @RequestParam(required = false) MultipartFile file) {
        if (file.isEmpty()) {
            return "index";
        }
        try {
            FileEntity fileEntity = new FileEntity(new Date(), file.getBytes());
            fileEntityService.addFileEntity(fileEntity);
            model.addAttribute("hash_id", Integer.toHexString(fileEntity.getHash()));
            return "view";
        } catch (IOException e) {
            throw new UploadedFileInvalidException();
        }
    }

    @RequestMapping("/view_file")
    public ModelAndView onViewPage(@RequestParam(value = "hash_id", required = false) String hashId) {
        if (hashId == null) {
            return new ModelAndView("view");
        }
        return new ModelAndView("view", "hash_id", hashId);
    }

    @RequestMapping("/file/{hash_id}")
    public ResponseEntity<byte[]> onPhoto(@PathVariable("hash_id") String hashId) {
        return getFileByHash(hexStringToInt(hashId));
    }

    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ResponseEntity<byte[]> onView(@RequestParam("hash_id") String hashId) {
        return getFileByHash(hexStringToInt(hashId));
    }

    @RequestMapping("/delete/{hash_id}")
    public String onDelete(@PathVariable("hash_id") String hashId) {
        if (fileEntityService.deleteByHashId(hexStringToInt(hashId)) == false) {
            throw new UploadedFileNotFoundException();
        } else {
            return "index";
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView onDeleteArray(@RequestParam(value = "hash_id", required = false) String[] hashIdArray) {
        if (hashIdArray != null) {
            for (String i : hashIdArray) {
                fileEntityService.deleteByHashId(hexStringToInt(i));
            }
        }
        return onList();
    }

    private ResponseEntity<byte[]> getFileByHash(int hashId) {
        byte[] bytes = fileEntityService.getByHashId(hashId).getFileData();
        if (bytes == null) {
            throw new UploadedFileNotFoundException();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
    }

    private int hexStringToInt(String hexString) {
        return (int) Long.parseLong(hexString, 16);
    }
}
