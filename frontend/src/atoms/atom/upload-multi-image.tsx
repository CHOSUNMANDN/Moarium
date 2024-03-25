import React, { ChangeEvent, useState } from 'react';

type dataType = {
  title: string;
  dataname: string;
  value?: string;
};

export default function UploadMultiImage(data: dataType & { userData: any; setUserData: any }) {
  const [previewImages, setPreviewImages] = useState<any[]>([]);

  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const uploadFile = e.target.files;
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
  };

  return (
    <div className="w-[100%] mb-[0.5rem] relative">
      <label htmlFor={data.dataname} className="font-bold">
        {data.title}
      </label>
      <div id="current_file">
        <input type="file" id="profile-upload" accept="image/*" multiple onChange={handleImageChange} />
      </div>

      <div className="form-group">
        <label htmlFor="subject">사진 미리보기</label>
        <div id="preview_img">
          {previewImages.map(image => (
            <div key={image.id} className="img_div relative">
              <img src={image.src} className="img_div_img" />
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
