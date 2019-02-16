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
    public ModelAndView onViewPage(@RequestParam(value = "hash_id", required = false) String stringHexHashId) {
        if ((stringHexHashId == null) || (stringHexHashId.equals(""))) {
            return new ModelAndView("view");
        }
        if (!fileEntityService.existsByHashId(stringHexHashIdToIntHashId(stringHexHashId))) {
            throw new UploadedFileNotFoundException();
        }
        return new ModelAndView("view", "hash_id", stringHexHashId);
    }

    @RequestMapping("/file/{hash_id}")
    public ResponseEntity<byte[]> onPhoto(@PathVariable("hash_id") String stringHexHashId) {
        return getFileByHash(stringHexHashIdToIntHashId(stringHexHashId));
    }

    @RequestMapping("/delete/{hash_id}")
    public String onDelete(@PathVariable("hash_id") String stringHexHashId) {
        if (!fileEntityService.deleteByHashId(stringHexHashIdToIntHashId(stringHexHashId))) {
            throw new UploadedFileNotFoundException();
        } else {
            return "index";
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView onDeleteArray(@RequestParam(value = "hash_id", required = false) String[] stringHexHashIdArray) {
        if (stringHexHashIdArray != null) {
            for (String i : stringHexHashIdArray) {
                fileEntityService.deleteByHashId(stringHexHashIdToIntHashId(i));
            }
        }
        return onList();
    }

    private ResponseEntity<byte[]> getFileByHash(int hashId) {
        FileEntity fileEntity = fileEntityService.getByHashId(hashId);
        if (fileEntity == null) {
            throw new UploadedFileNotFoundException();
        }
        byte[] fileData = fileEntity.getFileData();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
    }

    private int stringHexHashIdToIntHashId(String stringHexHashId) {
        int intHashId;
        try {
            intHashId = (int) Long.parseLong(stringHexHashId, 16);
        } catch (NumberFormatException e) {
            throw new UploadedFileNotFoundException();
        }
        return intHashId;
    }
}
