import { axAuth } from '@/apis/axiosinstance';
import LongThinButton from '../atom/LongThinButton';
import ShortButton from '../atom/ShortButton';
import { useRecoilState } from 'recoil';
import { userToken } from '../../states/index';
import { useEffect, useState } from 'react';
import { data } from 'autoprefixer';

type props = {
  clubId: string | undefined;
  clubMemberId: number;
  vacationToken: string;
  setIsMemberInfoOpen: (isOpen: number) => void;
  isKing: boolean;
  type: number;
};

type data = {
  name: string;
  major: string;
  studentId: string;
  selfIntroduction: string;
};

export default function MemberInformationModal({ clubId, clubMemberId, vacationToken, setIsMemberInfoOpen, isKing, type }: props) {
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
      url: `/clubs/informations/${clubId}/details/${clubMemberId}`,
    })
      .then(res => {
        setUserInfo(res.data.result);
      })
      .catch(err => {
        console.log(err);
      });
  }, []);

  const giveExpulsion = () => {
    axAuth(token)({
      method: 'post',
      url: `/clubs/informations/${clubId}/details/${clubMemberId}/expulsion`,
    })
      .then(res => {
        handleModal();
      })
      .catch(err => {
        handleModal();
      });
  };

  const giveDormancy = () => {
    axAuth(token)({
      method: 'post',
      url: `/clubs/informations/${clubId}/details/${clubMemberId}/dormancy`,
    })
      .then(res => {
        handleModal();
      })
      .catch(err => {
        handleModal();
      });
  };

  const giveVacation = () => {
    axAuth(token)({
      method: 'post',
      url: `/clubs/informations/${clubId}/details/${clubMemberId}/vacation`,
      data: {
        vacationToken: vacationCount,
      },
    })
      .then(res => {
        setVacationCount(0);
        handleModal();
      })
      .catch(err => {
        setVacationCount(0);
        handleModal();
      });
  };

  return (
    <div className="absolute z-10 w-[100vw] h-[100vh] bg-black/60 flex justify-center items-center" onClick={handleModal}>
      <div className="bg-white w-[20rem] h-[24rem] rounded-[4%] flex flex-col p-[1rem]" onClick={e => e.stopPropagation()}>
        {userInfo ? (
          <>
            <div className="flex items-center font-bold text-left mb-[1rem]">
              <div className="text-xl mr-[3rem]">{userInfo.name}</div>
              {isKing && type == 0 ? (
                <>
                  <div className="mr-[1rem]" onClick={giveExpulsion}>
                    <ShortButton text="추방하기" addClass="bg-red"></ShortButton>
                  </div>
                  <div onClick={giveDormancy}>
                    <ShortButton text="휴먼 전환" addClass="bg-orange"></ShortButton>
                  </div>
                </>
              ) : null}
            </div>
            <div className="flex place-content-between mb-[0.2rem]">
              <span className="font-semibold text-base">전공: {userInfo.major}</span>
              <span className="font-semibold text-base">학번: {userInfo.studentId}</span>
            </div>
            <div className="font-semibold text-base mb-[0.2rem]">자기소개</div>
            <div className="w-[17.81rem] h-[9.69rem] border border-grey rounded-[0.63rem] p-[0.5rem] mb-[1rem]">
              <span className="font-semibold text-base">{userInfo.selfIntroduction}</span>
            </div>
            {isKing && type == 0 ? (
              <>
                <div>
                  <div className="font-semibold text-base">남은 휴가 : {vacationToken}일</div>
                  <div className="flex items-center mb-[1rem]">
                    <span className="font-semibold text-base">휴가 제공 : </span>
                    <input type="number" className="border w-[5.56rem] h-[1.81rem] m-[0.2rem]" onChange={inputVacation} />
                    <span className="font-semibold text-base mr-[0.5rem]">일</span>
                    <div onClick={giveVacation}>
                      <ShortButton text="부여하기" addClass="bg-red"></ShortButton>
                    </div>
                  </div>
                </div>
              </>
            ) : null}
          </>
        ) : null}
        <div className="flex justify-center mt-auto" onClick={handleModal}>
          <LongThinButton text={'확인'} />
        </div>
      </div>
    </div>
  );
}
