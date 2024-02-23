import Link from 'next/link';

interface data {
  clubName: string;
}

export default function Header({ clubName }: data) {
  return (
    <>
      <div className="flex items-center justify-between py-[1.3rem] ml-[7.5%]">
        <div className="w-[8rem] h-[4rem]">
          <Link href={'/'}>
            <img className="w-[100%] h-[100%]" src="/Images/MOA_Logo.png" alt="로고 이미지" />
          </Link>
        </div>
        <div className="text-center font-bold text-[2rem] mr-[2rem]">{clubName}</div>
      </div>
    </>
  );
}
