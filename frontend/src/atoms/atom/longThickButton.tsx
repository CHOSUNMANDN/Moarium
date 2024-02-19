type data = {
  text: string;
  addClass?: string;
};

export default function LongThickButton({ text, addClass }: data) {
  return <button className={`w-[16.88rem] h-[2.75rem] bg-blue rounded-[0.63rem] text-white font-bold ${addClass}`}>{text}</button>;
}
