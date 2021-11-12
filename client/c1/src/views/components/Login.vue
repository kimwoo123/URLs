<template>
  <div id="option">
    <template v-if="this.getToken && this.getUsername">
      <div class="duration">
        <p>
          현재 로그인 중입니다.
          <el-button
            type="danger"
            icon="el-icon-close"
            circle
            @click="deleteToken"
          ></el-button>
        </p>
        <p></p>
      </div>

      <aside class="profile-card">
        <header>
          <!-- here’s the avatar -->
          <a target="_blank" href="#">
            <img v-bind:src="getPhotoUrl" class="hoverZoomLink" />
          </a>
          <h1>{{ getUsername }}</h1>
          <h2>
            {{ getEmail }}
          </h2>
        </header>
      </aside>
    </template>
    <template v-else
      ><div><h2>현재 토큰이 없습니다.</h2></div>
      <div>
        <p>{{ this.getToken }}</p>
        <p>{{ this.getUsername }}</p>
        <div>
          <img
            @click="signIn"
            style="width: 200px; height: 60px;"
            src="/icons/btn_google_signin_dark_disabled_web@2x.png"
          />
        </div></div
    ></template>
  </div>
</template>

<script>
import {getAuth, GoogleAuthProvider, signInWithPopup} from 'firebase/auth';
import {mapGetters} from 'vuex';
import mainApi from '../../api/mainApi';
import {mixin} from '../../utils/mixin';

export default {
  data() {
    return {
      username: null,
      token: null,
      saved: false,
    };
  },

  computed: {
    ...mapGetters(['getUsername', 'getToken', 'getEmail', 'getPhotoUrl']),
  },

  methods: {
    initComponent() {
      this.username = this.$store.getters.getUsername;
      this.token = this.$store.getters.getToken;
      this.email = this.$store.getters.getEmail;
      this.photoURL = this.$store.getters.getPhotoUrl;
    },

    deleteToken() {
      const payload = {
        username: null,
        email: null,
        token: null,
      };
      this.save(payload, 'updated');
    },
    async signIn() {
      const provider = new GoogleAuthProvider();
      const auth = getAuth();

      await signInWithPopup(auth, provider)
        .then(result => {
          const credential = GoogleAuthProvider.credentialFromResult(result);
          const token = credential.idToken;
          const {email, displayName, photoURL} = result.user;
          this.username = displayName;
          this.token = token;
          this.photoURL = photoURL;
          this.email = email;
          const payload = {
            username: displayName,
            email,
            token,
            photoURL,
          };
          console.log(displayName);
          console.log(email);
          console.log(photoURL);
          console.log(token);
          this.save(payload, 'updated');
        })
        .catch(error => {
          const errorCode = error.code;
          const {errorMessage} = error;
          const {email} = error;
          const credential = GoogleAuthProvider.credentialFromError(error);
        });

      const response = await mainApi.signIn(
        this.email,
        this.username,
        this.photoURL,
      );
      if (response && response.status === 201) {
        console.log('서버 연결 완료');
      } else {
        console.log('서버 연결 실패');
      }
    },
  },

  mixins: [mixin],
};
</script>

<style lang="scss" scoped>
#option {
  position: relative;

  .username {
    font-size: 10px;

    .username-highlighter {
      font-size: 14px;
    }
  }

  .duration {
    position: absolute;
    right: 0;
    color: #909399;
    font-size: 1rem;
  }
}
</style>
