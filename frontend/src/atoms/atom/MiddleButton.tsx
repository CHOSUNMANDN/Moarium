type data = {
  text: string;
  addClass?: string;
};

export default function MiddleButton({ text, addClass }: data) {
  return <button className={`w-[11.25rem] h-[1.88rem] bg-blue rounded-[0.63rem] text-white font-bold ${addClass}`}>{text}</button>;
}
