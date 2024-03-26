import React, { ChangeEvent, useState } from 'react';

type dataType = {
  title: string;
  dataname: string;
  value?: string;
};

export default function UploadSingleImage(data: dataType & { userData: any; setUserData: any }) {
  const [previewImages, setPreviewImages] = useState<any[]>([]);

  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const uploadFile = e.target.files?.[0];

    // 파일 업로드
    if (uploadFile) {
      data.setUserData((prevData: any) => ({ ...prevData, [data.dataname]: uploadFile }));
    }

    // 파일 미리 보기
    if (uploadFile) {
      setPreviewImages([]);

      const fReader = new FileReader();
      fReader.readAsDataURL(uploadFile);

      fReader.onloadend = event => {
        const lastModified = uploadFile.lastModified;
        setPreviewImages(prevImages => [
          ...prevImages,
          {
            id: lastModified,
            src: event.target?.result,
          },
        ]);
      };
    }
  };

  return (
    <div className="w-[100%] mb-[0.5rem] relative">
      <div className="form-group">
        <label htmlFor={data.dataname} className="font-bold">
          {data.title}
        </label>
        <div id="current_file">
          <input type="file" id="profile-upload" accept="image/*" onChange={handleImageChange} />
        </div>
      </div>
      {previewImages.length != 0 ? (
        <div className="form-group">
          <div className="h-[2rem] flex justify-center items-center">사진 미리보기</div>
          <div className="flex justify-center ">
            {previewImages.map(image => (
              <div key={image.id}>
                <img src={image.src} className="h-[10rem] w-[10rem]" />
              </div>
            ))}
          </div>
        </div>
      ) : null}
    </div>
  );
}
