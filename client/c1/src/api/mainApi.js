import axios from 'axios';
import {listUrl} from './list_url';

axios.defaults.baseURL = listUrl.baeURL;

export default {
  getServerStatus() {
    return axios
      .get('/user')
      .then(res => res)
      .catch(error => error);
  },

  getRelease() {
    return axios
      .get(listUrl.release_url)
      .then(res => res)
      .catch(error => error);
  },
  inject(url, token) {
    return axios
      .post('/inject', {
        url,
        token,
      })
      .then(res => res)
      .catch(error => error);
  },

  getWorkspace() {
    // axios
    //   .get('/folder')
    //   .then(res => res)
    //   .catch(error => error);

    return {
      data: [
        {
          type: 'type',
          title: '안녕하세요',
          url: 'https://www.naver.com',
        },

        {
          type: 'type',
          title: '안녕하세요',
          url: 'https://www.naver.com',
        },

        {
          type: 'type',
          title: '안녕하세요',
          url: 'https://www.naver.com',
        },

        {
          type: 'type',
          title: '안녕하세요',
          url: 'https://www.naver.com',
        },
      ],
    };
  },
  signIn(token, email, nickname, avatar) {
    const config = {
      headers: {Authorization: `Bearer ${token}`},
    };
    return axios
      .post(
        '/user',
        {
          email,
          avatar,
          nickname,
        },
        config,
      )
      .then(res => res)
      .catch(error => error);
  },
};
