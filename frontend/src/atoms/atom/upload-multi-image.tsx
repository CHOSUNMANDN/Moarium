import React, { ChangeEvent, useState } from 'react';
import Slider from 'react-slick';

type dataType = {
  title: string;
  dataname: string;
  value?: string;
};

export default function UploadMultiImage(data: dataType & { userData: any; setUserData: any }) {
  const [previewImages, setPreviewImages] = useState<any[]>([]);

  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const uploadFile = e.target.files;

    if (uploadFile && uploadFile.length > 3) {
      alert('사진은 3개 이하 등록 가능합니다.');
      return;
    } else {
      if (uploadFile) {
        data.setUserData((prevData: any) => ({ ...prevData, [data.dataname]: uploadFile }));
      }

      if (uploadFile) {
        setPreviewImages([]);

        for (let i = 0; i < uploadFile.length; i++) {
          const file = uploadFile[i];
          const fReader = new FileReader();
          fReader.readAsDataURL(file);

          fReader.onloadend = event => {
            const lastModified = file.lastModified;
            setPreviewImages(prevImages => [
              ...prevImages,
              {
                id: lastModified,
                src: event.target?.result,
              },
            ]);
          };
        }
      }
    }
  };

  return (
    <div className="w-[100%] mb-[0.5rem] relative">
      <label htmlFor={data.dataname} className="font-bold">
        {data.title}
      </label>
      <div id="current_file">
        <input type="file" id="profile-upload" accept="image/*" multiple onChange={handleImageChange} />
      </div>

      {previewImages.length != 0 ? (
        <div className="form-group">
          <div className="h-[2rem] flex justify-center items-center">사진 미리보기</div>
          <div className="flex justify-center">
            {previewImages.map(image => (
              <div key={image.id} className="flex justify-center items-center w-[100%] h-[7rem] ">
                <img src={image.src} className="h-[6rem] w-[6rem]" />
              </div>
            ))}
          </div>
        </div>
      ) : null}
    </div>
  );
}
