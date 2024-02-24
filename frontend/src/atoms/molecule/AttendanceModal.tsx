import { axAuth } from '@/apis/axiosinstance';
import MiddleButton from '../atom/MiddleButton';
import { useRecoilState } from 'recoil';
import { userToken } from '../../states/index';
import { useState } from 'react';

interface AttendanceModalProps {
  setIsAttendanceModalOpen: (isOpen: boolean) => void;
  setAllertModalStatus: (status: number) => void;
}

export default function AttendanceModal(props: AttendanceModalProps) {
  const [token, setToken] = useRecoilState(userToken);
  const [attendanceNumber, setAttendanceNumber] = useState('');

  const handleModal = () => {
    props.setIsAttendanceModalOpen(false);
  };

  const inputNumber = (e: React.ChangeEvent<HTMLInputElement>) => {
    setAttendanceNumber(e.target.value);
  };

  const setAttendance = () => {
    axAuth(token)({
      method: 'post',
      url: '/attendance/number',
      data: {
        numOfAttendance: attendanceNumber,
      },
    })
      .then(res => {
        if (res.data.passAtNow === true) {
          handleModal();
          props.setAllertModalStatus(3);
        } else {
          handleModal();
          props.setAllertModalStatus(1);
        }
      })
      .catch(err => {
        handleModal();
        props.setAllertModalStatus(2);
      });
  };
  return (
    <div className="absolute z-10 w-[100vw] h-[100vh] bg-black/60 flex justify-center items-center" onClick={handleModal}>
      <div className="bg-white w-[18.750rem] h-[9.375rem] rounded-[7%] flex flex-col justify-center items-center" onClick={e => e.stopPropagation()}>
        <div className="font-bold text-xl">오늘의 번호를 입력해주세요</div>
        <input className="border-b-2 w-[50%] my-3" type="text" onChange={inputNumber} />
        <div onClick={setAttendance}>
          <MiddleButton text="출석" addClass="text-xl" />
        </div>
      </div>
    </div>
  );
}
