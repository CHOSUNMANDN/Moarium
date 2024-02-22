import { axAuth } from '@/apis/axiosinstance';
import Button from '../atom/thin-long-button';
import { useRecoilState } from 'recoil';
import { userToken } from '../../states/index';
import { useEffect, useState } from 'react';
import { data } from 'autoprefixer';

type props = {
  userId: number;
  setIsMemberInfoOpen: (isOpen: number) => void;
  isKing: boolean;
  type: number;
};

type data = {
  name: string;
  major: string;
  studentId: string;
  phoneNum: string;
  hobby: string;
  specialtySkill: string;
  mbti: string;
  nuriKing: boolean;
  email: string;
};

export default function MemberInformationModal({ userId, setIsMemberInfoOpen, isKing, type }: props) {
  const [token, setToken] = useRecoilState(userToken);
  const [userInfo, setUserInfo] = useState<data>();
  const [vacationCount, setVacationCount] = useState(0);

  const handleModal = () => {
    setIsMemberInfoOpen(0);
  };

  const inputVacation = (e: React.ChangeEvent<HTMLInputElement>) => {
    setVacationCount(parseInt(e.target.value));
  };

  useEffect(() => {
    axAuth(token)({
      method: 'get',
      url: `/main/detail/${userId}`,
    })
      .then(res => {
        // console.log(res.data);
        setUserInfo(res.data);
      })
      .catch(err => {
        console.log(err);
      });
  }, []);

  const giveVacation = () => {
    axAuth(token)({
      method: 'post',
      url: 'vacations/count',
      data: {
        vacationCount: vacationCount,
        userId: userId,
      },
    })
      .then(res => {
        setVacationCount(0);
        handleModal();
        console.log(res.data);
      })
      .catch(err => {
        setVacationCount(0);
        handleModal();
      });
  };

  return (
    <div className="absolute z-10 w-[100vw] h-[100vh] bg-black/60 flex justify-center items-center" onClick={handleModal}>
      <div className="bg-white w-[85%] rounded-[4%] flex flex-col p-[1rem]" onClick={e => e.stopPropagation()}>
        {userInfo ? (
          <>
            <div className="font-bold text-xl text-left mb-[1rem]">{userInfo.name}</div>
            <div className="flex place-content-between">
              <span className="font-semibold text-base">전공: {userInfo.major}</span>
              <span className="font-semibold text-base">학번: {userInfo.studentId}</span>
            </div>
            <div className="border border-light-grey mt-[0.3rem]"></div>
            <span className="font-semibold text-base">핸드폰 번호: {userInfo.phoneNum}</span>
            <div className="border border-light-grey mt-[0.3rem]"></div>
            <div className="flex place-content-between">
              <span className="font-semibold text-base">취미: {userInfo.hobby}</span>
              <span className="font-semibold text-base mr-5">특기: {userInfo.specialtySkill}</span>
            </div>
            <div className="border border-light-grey mt-[0.3rem]"></div>
            <div className="flex place-content-between">
              <span className={`font-semibold text-base ${isKing && type == 0 ? null : 'mb-[1rem]'}`}>MBTI: {userInfo.mbti}</span>
              <span className={`font-semibold text-base ${isKing && type == 0 ? null : 'mb-[1rem]'}`}>email : {userInfo.email}</span>
            </div>
            {isKing && type == 0 ? (
              <>
                <div className="border border-light-grey my-[0.3rem]"></div>
                <div className="mb-auto">
                  <div>
                    <span className="font-semibold text-base">휴가 제공 : </span>
                    <input type="number" className="border w-[7rem] mr-[1rem] mb-[1rem]" onChange={inputVacation} />
                    <button className="bg-red w-[4rem] rounded-[10%]" onClick={giveVacation}>
                      부여하기
                    </button>
                  </div>
                </div>
              </>
            ) : null}
          </>
        ) : null}
        <div className="w-[100%] flex justify-center" onClick={handleModal}>
          <Button text="확인" addClass="text-sm" />
        </div>
      </div>
    </div>
  );
}
