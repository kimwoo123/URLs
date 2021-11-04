FROM node:alpine as builder

WORKDIR /app
COPY package.json ./
COPY . .
RUN npm install
RUN npm run build

FROM nginx:stable-alpine
COPY --from=builder /app/dist/spa /usr/share/nginx/html

CMD ["nginx", "-g", "daemon off;"]
