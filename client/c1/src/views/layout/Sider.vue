<template>
  <div id="sider">
    <el-menu :default-active="defaultActive" :router="true">
      <el-menu-item
        v-for="route in routes"
        :key="route.name"
        :index="route.path"
      >
        <i :class="route.icon"></i>
        <span>{{ route.name }}</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
import showdown from 'showdown';
import mainApi from '../../api/mainApi';
import routes from '../../router/routes';
import {mixin} from '../../utils/mixin';

const converter = new showdown.Converter();

export default {
  data() {
    return {
      routes,
      checkedReleasesIds: [],
      fetchedReleases: [],
      urls: [],
    };
  },

  methods: {
    initComponent() {
      this.checkedReleasesIds = this.$store.getters.getCheckedReleasesIds;
    },

    async fetchWorkSpace() {
      this.loading = true;
      const response = await mainApi.getWorkspace();
      const payload = {
        urls: response.data,
      };
      this.save(payload, 'updated');
      this.loading = false;
    },

    async fetchReleaseNotes() {
      this.loading = true;
      const resp = await mainApi.getRelease();
      if (resp && resp.data) {
        this.fetchedReleases = resp.data.map(release => ({
          body: release.description,
          html: converter.makeHtml(release.body),
          created_at: release.date,
          id: release.title,
          name: release.version,
        }));
        console.log('fetchedReleases :', this.fetchedReleases);

        const payload = {
          fetchedReleases: this.fetchedReleases,
        };
        this.save(payload, 'updated');
      } else {
        throw new Error('No resp');
      }
      this.loading = false;
    },
  },

  computed: {
    defaultActive() {
      return this.$route.path;
    },

    hasNewRelease() {
      return this.checkedReleasesIds.length < this.fetchedReleases.length;
    },
  },

  created() {
    this.fetchReleaseNotes();
    this.fetchWorkSpace();
    this.initComponent();
    this.$root.$on('loaded:sider', () => {
      this.initComponent();
    });
  },

  beforeDestroy() {
    this.$root.$off('loaded:sider');
  },
  mixins: [mixin],
};
</script>

<style lang="scss" scoped>
@import '../../style/global.scss';

#sider {
  position: fixed;
  width: $sider-width;
  height: $sider-height;
  background-color: white;

  /deep/ .sider-badge {
    display: inline;

    .el-badge__content {
      right: 15px;
      top: 5px;
    }
  }
}
</style>
