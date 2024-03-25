# frontend/Dockerfile

# docker container의 바탕이 되는 image의 이름이다.
# alpine은 Alpine Linux는 매우 작고 경량화된 배포판
FROM node:16.18.0-alpine

# 이미지 내에서 명령어를 실행할(현 위치로 잡을) 디렉토리 설정
WORKDIR /home/node/next

# Copy the rest of the application files to the container
# Docker 이미지를 빌드할 때 파일이 컨테이너 내에 복사됩니다.
# 현재 폴더 (docker file)이 있는 파일의 전체를 (frontend)폴더을 컨테이너 WORKDIR의 안에 복사를 한다.
COPY . .

# Build the production version of the app
# 컨테이너가 시작되는 시점에서 실행이 된다.
RUN npm run build


# Copy the rest of the application files to the container
# Docker 이미지를 빌드할 때 파일이 컨테이너 내에 복사됩니다.
# 현재 폴더 (docker file)이 있는 파일의 전체를 (frontend)폴더을 컨테이너 WORKDIR의 안에 복사를 한다.
COPY . .

# 명령은 컨테이너가 시작될 때 실행됩니다.
CMD [ "npm" ,  "start"]