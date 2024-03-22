import { data } from 'autoprefixer';
import { imageConfigDefault } from 'next/dist/shared/lib/image-config';

type data = {
  type: string;
  day: string;
  possible: string | null;
};

export default function CheckBox({ type, possible }: data) {
  return (
    <>
      {type === 'ATTENDANCE' && possible === 'POSSIBLE' ? (
        <img className="w-[2rem] h-[2rem]" src="/Images/checkbox_check.png" alt="checkbox" />
      ) : type === 'ABSENCE' && possible === 'POSSIBLE' ? (
        <img className="w-[2rem] h-[2rem]" src="/Images/checkbox_red.png" alt="checkbox" />
      ) : type === 'VACATION' && possible === 'POSSIBLE' ? (
        <img className="w-[2rem] h-[2rem]" src="/Images/checkbox_vacation.png" alt="checkbox" />
      ) : type === 'UNDECIDED' && possible === 'POSSIBLE' ? (
        <img className="w-[2rem] h-[2rem]" src="/Images/checkbox_default.png" alt="checkbox" />
      ) : (
        <img className="w-[2rem] h-[2rem]" src="/Images/checkbox_impossible.png" alt="checkbox" />
      )}
    </>
  );
}
