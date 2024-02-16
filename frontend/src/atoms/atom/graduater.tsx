type data = {
  name: string;
};

export default function Graduater({ name }: data) {
  return (
    <>
      <div className="text-center">
        <div className="font-semibold">{name}</div>
      </div>
      <div className="border border-light-grey my-[0.5rem]"></div>
    </>
  );
}
