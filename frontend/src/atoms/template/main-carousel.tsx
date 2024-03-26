'use client'; // SSL이 아닌 CSL로 바꾸어 주는 명령어 => Carousel은 동적이라 SSL 작동이 불가한가 봄. 정확한 이유 모르겠음
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import './carousel.css';
import { useEffect, useState } from 'react';
import { axAuth, axBase } from '@/apis/axiosinstance';
import { useRecoilState } from 'recoil';
import { isNuriKing, userToken } from '@/states';
import { usePathname } from 'next/navigation';
import SmallButton from '../atom/thin-long-button';
import ThinButton from '../atom/thin-button';
interface clubInformationType {
  clubName: string;
  clubIntroduction: string;
  clubLeaderName: string;
  clubInformationImages: string[];
}
export default function MainCarousel() {
  const [clubInformations, setClubInformations] = useState<clubInformationType>();
  const [token, setToken] = useRecoilState(userToken);

  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    // autoplay: true,
    autoplaySpeed: 5000,
    pauseOnHover: true,
    vertical: false,
  };

  let id: string | undefined;
  const pathName: string = usePathname();

  if (typeof pathName === 'string') {
    id = pathName.split('/').pop();
  } else {
    // pathName이 undefined인 경우에 대한 처리
    id = undefined;
  }

  useEffect(() => {
    axAuth(token)({
      method: 'get',
      url: '/clubs/informations/' + id,
    })
      .then(res => {
        const result = res.data.result;
        setClubInformations(result);
      })
      .catch(err => {
        console.log(err);
      });
  }, []);

  return (
    <>
      <div className="overflow-x-hidden">
        <Slider {...settings} className="h-[25vh] mb-[5rem]">
          {clubInformations?.clubInformationImages.map((image, index) => (
            <div key={index}>
              <img className="bg-cover w-[100%] aspect-[7/4]" src={'data:image/png;base64,' + image} alt={`Image ${index}`} />
              <div className="absolute bottom-0 left-0 w-full h-1/6 gradient opacity-40"></div>
            </div>
          ))}
        </Slider>
      </div>
      <div>
        <div className="flex items-center flex-col ">
          <div className="text-2xl font-bold h-[3rem]">{clubInformations?.clubName}</div>
          <div className="h-[7rem]  text-center text-wrap pl-[1rem] pr-[1rem] mb-[2rem] overflow-y-auto">{clubInformations?.clubIntroduction}</div>
          <div className="font-bold mb-[2rem]">팀장 : {clubInformations?.clubLeaderName}</div>
          <div className="w-[10rem]">
            <SmallButton text={'지원서 작성'} />
          </div>
        </div>
      </div>
    </>
  );
}
