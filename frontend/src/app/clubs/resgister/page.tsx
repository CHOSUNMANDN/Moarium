'use client';
import React, { useState, useEffect } from 'react';
import Link from 'next/link';
import { useRecoilState } from 'recoil';
import { useRouter } from 'next/navigation';
import { isNuriKing, userToken } from '@/states';
import { axAuth, axBase } from '@/apis/axiosinstance';
import Header from '@/atoms/molecule/header';
import MainCarousel from '@/atoms/template/main-carousel';
import { usePathname } from 'next/navigation';
import SmallCircle from '@/atoms/atom/small-circle';
import TextAreaForm from '@/atoms/atom/text-area-form-value';
import { CHECK_TYPE, TEXT_TYPE } from '@/utils/constans/custumType';
import CustomCheckForm from '@/atoms/atom/custom-check-form';
import SmallButton from '@/atoms/atom/thin-long-button';
import CustomTextAreaForm from '@/atoms/atom/custom-text-area-form';
import CommonAltertModal from '@/atoms/atom/common-allert-modal';
import { MODAL_TITLE_DANGER, MODAL_TITLE_SUCCESS } from '@/utils/constans/modalTitle';
import { checkHttpStatus } from '@/utils/validate/httpStatusChecker';
import ThinButton from '@/atoms/atom/thin-button';
import InputForm from '@/atoms/atom/input-form-value';
import UploadSingleImage from '@/atoms/atom/upload-single-image';
import ResponseMessage from '@/atoms/atom/response-message';
import UploadMultiImage from '@/atoms/atom/upload-multi-image';

interface ClubResgisterDataType {
  clubName: string;
  clubIntroduction: string;
  clubProfileImages: File | null;
  clubInformationImages: File[]; // 이미지를 base64 문자열로 표현
}

export default function Login() {
  const [isModalOpen, setModalOpen] = useState(false);
  const router = useRouter();
  const [token, setToken] = useRecoilState(userToken);
  let id: string | undefined;
  const pathName: string = usePathname();
  const [userData, setUserData] = useState<ClubResgisterDataType>({
    clubName: '',
    clubIntroduction: '',
    clubProfileImages: null,
    clubInformationImages: [],
  });
  const [formData, setFormData] = useState<FormData>(new FormData());
  const [message, setMessage] = useState('');
  const [overlapMessage, setOverlapMessage] = useState('');
  const [httpStatusMessage, setHttpStatusMessage] = useState('');
  const [overlapHttpStatusMessage, setOverlapHttpStatusMessage] = useState('');
  const placeHolder = '100자 이상 입력해주세요';

  if (typeof pathName === 'string') {
    id = pathName.split('/').pop();
  } else {
    // pathName이 undefined인 경우에 대한 처리
    id = undefined;
  }

  useEffect(() => {
    setUserData(userData);
  }, [userData]);

  const resgisterButton = () => {
    const formData = new FormData();
    formData.append('clubName', userData.clubName);
    formData.append('clubIntroduction', userData.clubIntroduction);
    // clubProfileImages가 null이 아닌 경우에만 formData에 추가
    if (userData.clubProfileImages) {
      formData.append('clubProfileImages', userData.clubProfileImages);
    }
    console.log(formData.get('clubProfileImages'));

    if (userData.clubInformationImages) {
      for (let i = 0; i < userData.clubInformationImages.length; i++) {
        formData.append('clubInformationImages', userData.clubInformationImages[i]);
      }
    }

    axAuth(token)({
      method: 'post',
      url: '/main/club/apply',
      data: formData,
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    })
      .then(res => {
        setModalOpen(true);
        const message = res.data.message;
        setMessage(message);
        setHttpStatusMessage(res.data.httpStatus);
        console.log(message);
      })
      .catch(err => {
        setModalOpen(true);
        const message = err.response.data.message;
        console.log(message);
        setMessage(message);
        setHttpStatusMessage(err.response.data.httpStatus);
      });
  };

  const pushCheckOverlap = () => {
    axAuth(token)({
      method: 'post',
      url: '/main/club/apply/overlap',
      data: {
        clubName: userData.clubName,
      },
    })
      .then(res => {
        const message = res.data.message;
        console.log(res);
        setOverlapMessage(message);
        setOverlapHttpStatusMessage(res.data.httpStatus);
      })
      .catch(err => {
        const message = err.response.data.message;
        setOverlapMessage(message);
        setOverlapHttpStatusMessage(err.response.data.httpStatus);
      });
  };

  useEffect(() => {
    if (isModalOpen === true) {
      setTimeout(() => {
        setModalOpen(false); // 2초 후에 AllertModal 닫기
      }, 3000);
    }
  }, [isModalOpen]);

  // 테이터 변경시 FormData에 저장
  useEffect(() => {
    setUserData(userData);
    console.log(userData);
  }, [userData]);

  const validation = () => {
    if (userData.clubIntroduction.length < 100) {
      alert('팀 소개를 100자 이상 입력해주세요.');
    }
    if (userData.clubName.length >= 25) {
      alert('팀 이름은 25글자 이하 입니다.');
    } else {
      resgisterButton();
    }
  };

  const clubNameValidation = () => {
    if (userData.clubName.length >= 25) {
      alert('팀 이름은 25글자 이하 입니다.');
    } else {
      pushCheckOverlap();
    }
  };

  return (
    <>
      <header>
        <Header isVisible={false} />
      </header>
      <main>
        {isModalOpen ? <CommonAltertModal title={checkHttpStatus(httpStatusMessage) ? MODAL_TITLE_SUCCESS : MODAL_TITLE_DANGER} context={message} type={httpStatusMessage} /> : null}
        <article>
          <div className="ml-[2rem] mr-[2rem]">
            <div className="font-bold text-2xl  h-[3rem]">동아리 등록</div>
            <div>
              <div className="flex">
                <div className="w-[66%]">
                  <InputForm title={'팀 이름'} dataname={'clubName'} userData={userData} setUserData={setUserData}></InputForm>
                </div>
                <div className="flex flex-col items-center w-[33%]">
                  <div className="h-[1.4rem]"></div>
                  <div className="w-[5rem]" onClick={clubNameValidation}>
                    <SmallButton text={'중복 확인'}></SmallButton>
                  </div>
                </div>
              </div>
              <div>
                <ResponseMessage message={overlapMessage} type={overlapHttpStatusMessage} />
              </div>
            </div>

            <div>
              <TextAreaForm title="팀 소개 (100자 이상)" placeholder={placeHolder} dataname={'clubIntroduction'} userData={userData} setUserData={setUserData} />
            </div>
            <div className="border">
              <div>
                <UploadSingleImage title={'팀 프로필 사진'} dataname={'clubProfileImages'} userData={userData} setUserData={setUserData} />
              </div>
              <div></div>
            </div>
            <div className="border">
              <div>
                <UploadMultiImage title={'팀 소개 사진'} dataname={'clubInformationImages'} userData={userData} setUserData={setUserData} />
              </div>
              <div></div>
            </div>
            <div onClick={validation} className="mb-[5rem] mt-[5rem]">
              <SmallButton text={'지원하기'} />
            </div>
          </div>
        </article>
      </main>
    </>
  );
}
