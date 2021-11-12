<template>
  <div id="option">
    <template v-if="this.getUsername && this.getToken"
      ><el-row>
        <el-form
          ref="form"
          label-width="80px"
          label-position="left"
          size="small"
        >
          <el-form-item label="url">
            <el-input
              v-model="url"
              size="small"
              placeholder="url을 입력해주세요"
            ></el-input>
          </el-form-item>
          <el-form-item label="카테고리">
            <el-select
              v-model="serviceType"
              placeholder="카테고리를 적어주세요"
            >
              <el-option-group
                v-for="group in serviceTypes"
                :key="group.label"
                :label="group.label"
              >
                <el-option
                  v-for="item in group.options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                  <span style="float: left">{{ item.label }}</span>
                </el-option>
              </el-option-group>
            </el-select>
          </el-form-item>
        </el-form>
        <el-button
          :type="saved ? 'success' : 'primary'"
          size="small"
          @click="inject"
        >
          <span v-if="saved">
            <i class="el-icon-check"></i>
            URL 저장됨
          </span>
          <span v-else-if="net_false">네트워크를 확인해주세요</span>
          <span v-else>URL 저장</span>
        </el-button>
      </el-row>
    </template>
    <template v-else>
      <div class="no-info">
        <i class="el-icon-s-promotion icon"></i>
        <h2>URL 입력은 로그인을 해야 가능합니다.</h2>
        <p>로그인을 진행해주세요</p>
        <router-link to="/">
          <el-button>바로가기</el-button>
        </router-link>
      </div>
    </template>
  </div>
</template>

<script>
import {mapGetters} from 'vuex';
import mainApi from '../../api/mainApi';
import {CATEGORY} from '../../api/list_url';
import {mixin} from '../../utils/mixin';

export default {
  data() {
    return {
      url: null,
      serviceType: null,
      serviceTypes: CATEGORY,
      saved: false,
      net_false: false,
    };
  },

  computed: {
    ...mapGetters(['getToken', 'getUsername']),
  },

  methods: {
    initComponent() {},
    async inject() {
      const response = await mainApi.inject();
      if (response.status === 200) {
        this.saved = true;
      } else {
        this.net_false = true;
      }
    },
  },

  mixins: [mixin],
};
</script>

<style lang="scss" scoped>
#option {
  position: relative;
  .no-info {
    display: flex;
    justify-content: center;
    vertical-align: middle;
    flex-direction: column;
    text-align: center;
    height: 230px;

    .icon {
      font-size: 30px;
    }
  }
}
</style>
