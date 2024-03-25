import React, { ChangeEvent, useState } from 'react';

type dataType = {
  title: string;
  dataname: string;
  value?: string;
};

export default function UploadSingleImage(data: dataType & { userData: any; setUserData: any }) {
  const onChangeOneImg = (e: ChangeEvent<HTMLInputElement>) => {
    e.preventDefault();
    const uploadFile = e.target.files?.[0];
    if (uploadFile) {
      data.setUserData((prevData: any) => ({ ...prevData, [data.dataname]: uploadFile }));
    }
  };

  return (
    <div className="w-[100%] mb-[0.5rem] relative">
      <label htmlFor={data.dataname} className="font-bold">
        {data.title}
      </label>
      <input type="file" id="profile-upload" accept="image/*" onChange={onChangeOneImg} />
    </div>
  );
}
