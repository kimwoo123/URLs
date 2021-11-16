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
  provideConfig(token) {
    return {
      headers: {
        'Content-Type': 'application/json;',
        Authorization: `Bearer ${token}`,
      },
    };
  },
  signIn(token, payload) {
    return axios
      .post('/user', payload, this.provideConfig(token))
      .then(res => res)
      .catch(error => error);
  },
  getFolders(token) {
    return axios
      .get('/folder/me', this.provideConfig(token))
      .then(result => result)
      .catch(error => error);
  },
};
