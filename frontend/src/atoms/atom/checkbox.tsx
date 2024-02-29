import { data } from 'autoprefixer';
import { imageConfigDefault } from 'next/dist/shared/lib/image-config';

type data = {
  type: string;
  day: string;
};

export default function CheckBox({ type }: data) {
  return (
    <>
      {type === 'ATTENDANCE' ? (
        <img className="w-[2rem] h-[2rem]" src="/Images/checkbox_check.png" alt="checkbox" />
      ) : type === 'ABSENCE' ? (
        <img className="w-[2rem] h-[2rem]" src="/Images/checkbox_red.png" alt="checkbox" />
      ) : type === 'VACATION' ? (
        <img className="w-[2rem] h-[2rem]" src="/Images/checkbox_vacation.png" alt="checkbox" />
      ) : (
        <img className="w-[2rem] h-[2rem]" src="/Images/checkbox_default.png" alt="checkbox" />
      )}
    </>
  );
}
