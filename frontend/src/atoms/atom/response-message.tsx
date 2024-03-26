import React, { useState } from 'react';
import SmallCircle from './small-circle';
import { HTTP_BAD_REQUEST, HTTP_STATUS_OK } from '@/utils/constans/httpStatusEnum';

type dataType = {
  message: string;
  type: string;
};

export default function ResponseMessage(data: dataType) {
  return (
    <div className="h-[2.8rem]  text-sm text-wrap  overflow-y-auto">
      {data.type === HTTP_BAD_REQUEST ? (
        <>
          <div className="text-[#f67777]">{data.message}</div>
        </>
      ) : data.type === HTTP_STATUS_OK ? (
        <>
          <div className="text-[#779ff6]">{data.message}</div>
        </>
      ) : null}
    </div>
  );
}
