FROM node:18-bookworm

WORKDIR /app

COPY package.json yarn.lock ./
RUN yarn install --frozen-lockfile

COPY . .
RUN yarn build

ENV NODE_ENV production
ENV PORT 3000

EXPOSE 3000

ENTRYPOINT []
CMD ["yarn", "run" "start"]
