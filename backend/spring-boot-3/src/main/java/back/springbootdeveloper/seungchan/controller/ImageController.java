package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.dto.response.ImageResDto;
import back.springbootdeveloper.seungchan.service.ImageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "사진 업로드 테스트 코드", description = "사진 업로드 등록 테스트 코드")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/images")
public class ImageController {
    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    // https://easthshin.tistory.com/m/15
    // @RequestPart 는 name 속성을 통해 key 값을 지정해 바인딩 해줄 수 있는데,
    // 지정하지 않으면 디폴트로 변수명을 key 값으로 갖기 때문에 특별히 이유가 있지 않다면 따로 지정해주지 않아도 된다.
    @PostMapping
    public ResponseEntity<ImageResDto> handleFileUpload(@RequestParam(value = "file") MultipartFile image) {
        ImageResDto imageResponse = imageService.uploadImage(image);
        return ResponseEntity.ok(imageResponse);
    }
}
/**
 * "form-data": "^4.0.0",
 * "formidable": "^3.5.1",
 * 'use client';
 * import axios from 'axios';
 * import { SetStateAction, ChangeEvent, useState } from 'react';
 * import { axBase } from '@/apis/axiosinstance';
 * <p>
 * export default function Home() {
 * const [file, setFile] = useState<File | null>(null);
 * <p>
 * const onChangeImg = (e: ChangeEvent<HTMLInputElement>) => {
 * e.preventDefault();
 * const formData = new FormData();
 * <p>
 * const uploadFile = e.target.files?.[0];
 * if (uploadFile) {
 * formData.append('scheduleImage', uploadFile);
 * setFile(uploadFile);
 * <p>
 * console.log('===formData===');
 * console.log(formData.get('scheduleImage'));
 * console.log('===uploadFile===');
 * console.log(uploadFile);
 * console.log('===useState===');
 * console.log(file);
 * <p>
 * axBase()({
 * method: 'post',
 * url: '/clubs/schedules/manual',
 * data: formData,
 * })
 * .then(response => {
 * console.log(response.data);
 * })
 * .catch(err => {});
 * }
 * };
 * <p>
 * return (
 * <>
 * <form encType="multipart/form-data">
 * <input type="file" id="profile-upload" accept="image/*" onChange={onChangeImg} />
 * </form>
 * </>
 * );
 * }
 */