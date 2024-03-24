import React, { useState } from 'react';
import SmallCircle from './small-circle';

type data = {
  title: string;
  dataname: string;
  placeholder?: string;
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

export default function CustomTextAreaForm({ title, userData, setUserData, customInformationId, placeholder, dataname }: data & { userData: PostApplyDataType; setUserData: any }) {
  const [textLength, setTextLength] = useState(0);

  // CustomTextAreaForm 컴포넌트
  const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const { value } = e.target;
    setTextLength(value.length);

    setUserData((prevData: PostApplyDataType) => {
      const existingIndex = prevData.postCustomInformations.findIndex(info => info.customInformationId === customInformationId);

      if (existingIndex !== -1) {
        // 이미 같은 customInformationId를 가진 요소가 있는 경우
        const updatedCustomInformations = [...prevData.postCustomInformations];
        updatedCustomInformations[existingIndex] = {
          customInformationId: customInformationId,
          customContent: value,
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
              customContent: value,
            },
          ],
        };
      }
    });
  };

  return (
    <div className="w-[100%] mb-[0.5rem] relative">
      <label htmlFor={`${title}`} className="font-bold">
        <SmallCircle></SmallCircle>
        {title}
      </label>
      <span className="font-semibold absolute right-[1rem]">{textLength}자</span>
      <br />
      <textarea name={`${title}`} className={`border border-grey rounded w-[100%] h-[12rem]`} maxLength={500} onChange={handleChange} placeholder={placeholder} />
    </div>
  );
}
