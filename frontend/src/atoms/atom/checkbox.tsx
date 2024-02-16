import { data } from 'autoprefixer';
import { imageConfigDefault } from 'next/dist/shared/lib/image-config';

type data = {
  type: string;
};

export default function CheckBox({ type }: data) {
  return (
    <>
      {type === 'check' ? (
        <img className="w-[2rem] h-[2rem]" src="/Images/checkbox_check.png" alt="checkbox" />
      ) : type === 'default' ? (
        <img className="w-[2rem] h-[2rem]" src="/Images/checkbox_default.png" alt="checkbox" />
      ) : type === 'vacation' ? (
        <img className="w-[2rem] h-[2rem]" src="/Images/checkbox_vacation.png" alt="checkbox" />
      ) : type === 'tardy' ? (
        <img className="w-[2rem] h-[2rem]" src="/Images/checkbox_tardy.png" alt="checkbox" />
      ) : (
        <img className="w-[2rem] h-[2rem]" src="/Images/checkbox_red.png" alt="checkbox" />
      )}
    </>
  );
}
