type data = {
  text: string;
  addClass?: string;
};

export default function MiddleButton({ text, addClass }: data) {
  return <button className={`w-[10.94rem] h-[2.5rem] bg-blue rounded-[0.63rem] text-white font-bold ${addClass}`}>{text}</button>;
}
