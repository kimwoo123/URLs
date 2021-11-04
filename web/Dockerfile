FROM node:alpine as builder

COPY ./package.json ./
RUN npm install
COPY . .
RUN npm run combined

FROM nginx:stable-alpine
COPY --from=builder ./dist /usr/share/nginx/html

CMD ["nginx", "-g", "daemon off;"]
