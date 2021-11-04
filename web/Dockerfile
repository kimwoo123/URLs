FROM node:alpine as builder

COPY ./package.json ./
RUN npm install
COPY . .
ENV QENV="development"
RUN npm run build

FROM nginx:stable-alpine
COPY --from=builder ./dist /usr/share/nginx/html

CMD ["nginx", "-g", "daemon off;"]
