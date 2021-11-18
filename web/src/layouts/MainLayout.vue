<template>
  <q-layout view="hHh LpR fFf">
    <q-header bordered class="bg-white text-black">
      <q-toolbar class="q-py-sm">
        <!-- <q-btn flat @click="drawer = !drawer" round dense icon="menu" /> -->
        <q-btn dense flat round icon="menu" @click="toggleLeftDrawer" />
        <q-toolbar-title>
          율스
        </q-toolbar-title>

        <!-- <q-btn flat round dense icon="fas fa-bell" class="q-mr-md">
          <q-badge floating color="red">2</q-badge>
        </q-btn> -->

        <!-- <q-btn flat no-caps class="q-mx-xs" @click="goToSettings">
          <q-avatar size="36px">
            <img :src="avatarUrl" />
          </q-avatar>
          <div>{{ username }}</div>
        </q-btn> -->
      </q-toolbar>
    </q-header>

    <q-drawer
      v-model="leftDrawerOpen"
      side="left"
      bordered
      :width="250"
      :breakpoint="900"
      class="scroll overflow-hidden"
    >
      <q-scroll-area style="height: calc(100% - 110px); margin-top: 110px; border-right: 1px solid #ddd">
        <left-drawer />
      </q-scroll-area>

      <q-img class="absolute-top" src="https://cdn.quasar.dev/img/material.png" style="height: 110px">
        <div class="absolute-bottom bg-transparent column">
          <div class="row">
              <q-item clickable class="q-pa-none" @click="goToSettings">
                <q-item-section avatar>
                  <q-avatar size="48px" class="q-mb-sm">
                    <img :src="avatarUrl">
                  </q-avatar>
                </q-item-section>
                <q-item-section>
                  <div class="text-weight-bold">
                    {{ username }}
                    <q-icon name="settings" />
                  </div>
                </q-item-section>
              </q-item>
          </div>
          <div class="text-weight-light">{{ useremail }}</div>
        </div>
      </q-img>
    </q-drawer>

    <q-drawer
      v-model="rightDrawerOpen"
      side="right"
      bordered
      :width="300"
      :breakpoint="500"
      class="scroll overflow-hidden"
    >
      <right-drawer/>

    </q-drawer>

    <q-page-container>
      <router-view />

      <q-page-scroller position="bottom-right" :scroll-offset="150" :offset="[18, 18]">
        <q-btn fab icon="keyboard_arrow_up" color="primary" />
      </q-page-scroller>
    </q-page-container>
  </q-layout>
</template>

<script>
import { defineComponent, ref, watch, computed, onMounted } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { debounce } from 'quasar'
import LeftDrawer from "src/components/drawer/LeftDrawer.vue";
import RightDrawer from "src/components/drawer/RightDrawer.vue";

export default defineComponent({
  components: { LeftDrawer, RightDrawer },
  name: "MainLayout",
  setup() {
    const $store = useStore();
    const $router = useRouter();

    const isUrlMemoOpen = computed({
      get: () => $store.getters["urls/isUrlMemoOpen"]
    });

    const username = $store.state.user.username;
    const useremail = $store.state.user.useremail;
    const userid = $store.state.user.userid;
    const avatarUrl = $store.state.user.avatar;

    const goToSettings = () => {
      $router.push({ name: "Settings", params: { id: userid } });
    };

    const leftDrawerOpen = ref(true);
    const rightDrawerOpen = ref(false);

    watch(isUrlMemoOpen, () => {
      if (isUrlMemoOpen.value === false) {
        rightDrawerOpen.value = false;
      } else {
        rightDrawerOpen.value = true;
      }
    });

    watch(rightDrawerOpen, () => {
      if (rightDrawerOpen.value === true) {
        $store.dispatch("urls/OPEN_MEMO");
      } else {
        $store.dispatch("urls/CLOSE_MEMO");
      }
    });

    function onScroll (position) {
      // when this method is invoked then it means user
      // has scrolled the page to `position`
      //
      // `position` is an Integer designating the current
      // scroll position in pixels.
    }

    return {
      // drawer,
      text: ref(""),
      username,
      userid,
      useremail,
      avatarUrl,

      goToSettings,
      // folders

      leftDrawerOpen,
      toggleLeftDrawer() {
        leftDrawerOpen.value = !leftDrawerOpen.value;
      },

      rightDrawerOpen,

      onScroll: debounce(onScroll, 200)
    };
  }
});
</script>

<style lang="scss">
body {
  font-family: -apple-system, BlinkMacSystemFont, "Apple SD Gothic Neo",
    Pretendard, Roboto, "Noto Sans KR", "Segoe UI", "Malgun Gothic", sans-serif;
}

a:link,
a:hover,
a:active,
a:visited {
  text-decoration: None;
  color: gray;
}
</style>
