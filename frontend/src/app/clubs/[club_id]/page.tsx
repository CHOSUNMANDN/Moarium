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

interface ClubApplyDataType {
  customInformations: CustomInformations[];
  clubName: string;
}

interface CustomInformations {
  customInformationId: number;
  customContent: string;
  customType: string;
}

interface PostCustomInformation {
  customInformationId: number;
  customContent: string;
}

interface PostApplyDataType {
  selfIntroduction: string;
  postCustomInformations: PostCustomInformation[];
}

export default function Login() {
  const [isModalOpen, setModalOpen] = useState(false);
  const router = useRouter();
  const [token, setToken] = useRecoilState(userToken);
  let id: string | undefined;
  const pathName: string = usePathname();
  const [clubApplyData, setClubApplyData] = useState<ClubApplyDataType>();
  const [userData, setUserData] = useState<PostApplyDataType>({
    selfIntroduction: '',
    postCustomInformations: [],
  });
  const [message, setMessage] = useState('');
  const [httpStatusMessage, setHttpStatusMessage] = useState('');
  const placeHolder = '동아리 회장에게만 공개 됩니다.';

  if (typeof pathName === 'string') {
    id = pathName.split('/').pop();
  } else {
    // pathName이 undefined인 경우에 대한 처리
    id = undefined;
  }

  useEffect(() => {
    axAuth(token)({
      method: 'get',
      url: '/clubs/' + id + '/apply/find',
    })
      .then(res => {
        const result = res.data.result;
        setClubApplyData(result);
      })
      .catch(err => {
        console.log(err);
      });
  }, []);

  useEffect(() => {
    setUserData(userData);
  }, [userData]);

  const applyClub = () => {
    axAuth(token)({
      method: 'post',
      url: '/clubs/' + id + '/apply',
      data: {
        selfIntroduction: userData.selfIntroduction,
        customInformations: userData.postCustomInformations,
      },
    })
      .then(res => {
        setModalOpen(true);
        const message = res.data.message;
        setMessage(message);
        setHttpStatusMessage(res.data.httpStatus);
      })
      .catch(err => {
        setModalOpen(true);
        const message = err.response.data.message;
        setMessage(message);
        setHttpStatusMessage(err.response.data.httpStatus);
      });
  };

  useEffect(() => {
    if (isModalOpen === true) {
      setTimeout(() => {
        setModalOpen(false); // 2초 후에 AllertModal 닫기
      }, 3000);
    }
  }, [isModalOpen]);

  const validation = () => {
    if (userData.selfIntroduction.length < 100) {
      alert('자기소개를 100자 이상 입력해주세요.');
    } else {
      applyClub();
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
            <div className="font-bold text-2xl  h-[3rem]">새 팀 지원하기</div>
            <div>
              <div className="flex">
                <SmallCircle />
                <div className="font-bold">팀 이름</div>
              </div>
              <div className="flex justify-center font-bold items-center h-[1.5rem] w-[8rem] border rounded-md mb-[1rem] mt-[.5rem]">{clubApplyData?.clubName}</div>
            </div>

            <div>
              <TextAreaForm title="자기 소개 (최소 100자)" placeholder={placeHolder} dataname={'selfIntroduction'} userData={userData} setUserData={setUserData} />
            </div>
            <div>
              {clubApplyData?.customInformations.map((item: CustomInformations, index) => (
                <div key={index}>
                  {item.customType == TEXT_TYPE ? (
                    <CustomTextAreaForm
                      title={item.customContent}
                      placeholder={placeHolder}
                      dataname={'postCustomInformations'}
                      userData={userData}
                      setUserData={setUserData}
                      customInformationId={item.customInformationId}
                    />
                  ) : null}
                  {item.customType == CHECK_TYPE ? (
                    <CustomCheckForm
                      title={item.customContent}
                      placeholder={placeHolder}
                      dataname={'postCustomInformations'}
                      userData={userData}
                      setUserData={setUserData}
                      customInformationId={item.customInformationId}
                    />
                  ) : null}
                </div>
              ))}
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
