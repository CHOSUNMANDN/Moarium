import React, { useState } from 'react';
import SmallCircle from './small-circle';

type data = {
  title: string;
  dataname: string;
};

export default function TextAreaForm({ title, userData, setUserData, dataname }: data & { userData: any; setUserData: any }) {
  const [textLength, setTextLength] = useState(0);

  const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setTextLength(value.length);
    setUserData((prevData: any) => ({ ...prevData, [dataname]: value }));
  };

  return (
    <div className="w-[100%] mb-[0.5rem] relative">
      <label htmlFor={`${title}`} className="font-bold">
        <SmallCircle></SmallCircle>
        {title}
      </label>
      <span className="font-semibold absolute right-[1rem]">{textLength}자</span>
      <br />
      <textarea name={`${title}`} className={`border border-grey rounded w-[100%] h-[12rem]`} maxLength={500} onChange={handleChange} />
    </div>
  );
}
