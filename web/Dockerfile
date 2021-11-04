FROM node:alpine as builder

COPY ./package.json ./
COPY . .
RUN npm install
RUN npm run dev

FROM nginx:stable-alpine
COPY --from=builder ./dist /usr/share/nginx/html

CMD ["nginx", "-g", "daemon off;"]
