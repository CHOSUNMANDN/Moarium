'use client';
import axios from 'axios';
import { SetStateAction, ChangeEvent, useState, useEffect } from 'react';
import { axBase } from '@/apis/axiosinstance';

interface ClubRegistrationReqDto {
  clubName: string;
  clubIntroduction: string;
  // 다른 필드들도 필요에 따라 추가
}

export default function Home() {
  const [formData, setFormData] = useState<FormData>(new FormData());

  useEffect(() => {
    // 토큰이 있을시 메인페이지 이동
    setFormData(formData);
    console.log('dsf');
  }, [formData]);

  const onChangeOneImg = (e: ChangeEvent<HTMLInputElement>) => {
    e.preventDefault();
    const uploadFile = e.target.files?.[0];
    if (uploadFile) {
      formData.append('clubProfileImages', uploadFile);
      setFormData(formData);
      const clubRegistrationReqDto: ClubRegistrationReqDto = {
        clubName: '팀 이름',
        clubIntroduction: '팀 자기소개',
        // 다른 필드들도 필요에 따라 추가
      };

      // formData.append('request', JSON.stringify(clubRegistrationReqDto));
      formData.append('clubName', clubRegistrationReqDto.clubName);
      formData.append('clubIntroduction', clubRegistrationReqDto.clubIntroduction);
    }
  };

  const onChangeImgs = (e: ChangeEvent<HTMLInputElement>) => {
    e.preventDefault();
    const uploadFiles = e.target.files;
    if (uploadFiles) {
      for (let i = 0; i < uploadFiles.length; i++) {
        formData.append('clubInformationImages', uploadFiles[i]);
      }
      setFormData(formData);
    }
  };

  const pushButton = () => {
    axBase()({
      method: 'post',
      url: '/main/club/apply',
      data: formData,
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    })
      .then(response => {
        console.log(response.data);
      })
      .catch(err => {});
  };

  const pushButton_2 = () => {
    axBase()({
      method: 'post',
      url: '/main/club/apply',
      data: {
        clubName: '클럽이름',
        clubIntroduction: '클럽자기소개',
      },
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    })
      .then(response => {
        console.log(response.data);
      })
      .catch(err => {});
  };

  return (
    <>
      <form encType="multipart/form-data">
        <input type="file" id="profile-upload" accept="image/*" onChange={onChangeOneImg} />
        <input type="file" id="profile-upload" accept="image/*" multiple onChange={onChangeImgs} />
        <div>
          <button onClick={pushButton}>사진이랑 보내기</button>
        </div>
        <div>
          <button onClick={pushButton_2}>사진없이 보내기</button>
        </div>
      </form>
    </>
  );
}
