import React, { useState } from 'react';
import SmallCircle from './small-circle';
import { CHECK, UN_CHECK } from '@/utils/constans/custumType';

type data = {
  title: string;
  dataname: string;
  placeholder?: string;
  value?: string;
  customInformationId: number;
};
interface PostApplyDataType {
  selfIntroduction: string;
  postCustomInformations: PostCustomInformation[];
}
interface PostCustomInformation {
  customInformationId: number;
  customContent: string;
}

export default function CustomCheckForm({ title, userData, customInformationId, setUserData, dataname }: data & { userData: PostApplyDataType; setUserData: any }) {
  const [isChecked, setIsChecked] = useState(false);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { checked } = e.target;
    setIsChecked(checked); // 체크박스의 실제 상태를 반영

    // 체크박스의 실제 상태에 따라 "CHECK" 또는 "UN_CHECK" 값을 저장
    setUserData((prevData: PostApplyDataType) => {
      const existingIndex = prevData.postCustomInformations.findIndex(info => info.customInformationId === customInformationId);

      if (existingIndex !== -1) {
        // 이미 같은 customInformationId를 가진 요소가 있는 경우
        const updatedCustomInformations = [...prevData.postCustomInformations];
        updatedCustomInformations[existingIndex] = {
          customInformationId: customInformationId,
          customContent: checked ? CHECK : UN_CHECK,
        };

        return {
          ...prevData,
          postCustomInformations: updatedCustomInformations,
        };
      } else {
        // 같은 customInformationId를 가진 요소가 없는 경우
        return {
          ...prevData,
          postCustomInformations: [
            ...prevData.postCustomInformations,
            {
              customInformationId: customInformationId,
              customContent: checked ? CHECK : UN_CHECK,
            },
          ],
        };
      }
    });
  };

  return (
    <div className="flex items-center w-[100%] mb-[0.5rem] relative">
      <label htmlFor={`${title}`} className="font-bold w-[80%]">
        <SmallCircle />
        {title}
      </label>
      <div className="flex items-center justify-center w-[20%]">
        <input type="checkbox" className="form-checkbox h-5 w-5 text-indigo-600" checked={isChecked} onChange={handleChange} />
      </div>
    </div>
  );
}
