<template>
  <main class="q-pa-sm">
    <!-- <auto-complete></auto-complete> -->
    <q-item class="q-pb-lg">
      <q-item-section avatar>
        <q-icon name="recommend" size="40px" class="folder-icon" />
      </q-item-section>
      <q-item-section class="text-h6">
        추천 페이지
      </q-item-section>
    </q-item>

    <!-- <q-list> -->
    <div class="row">
      <div class="row q-gutter-lg justify-center">
        <q-card 
          class="my-card col-xl-2 col-lg-3 col-md-4 col-sm-5 col-10" 
          flat bordered 
          v-for="urlItem in recommendUrls" 
          :key="urlItem"
        >
          <q-card-section horizontal >
            <q-card-section v-if="urlItem.title === null" class="col-7">
              <a :href="urlItem.url">
                당신이 좋아할 포스트
              </a>
            </q-card-section>
            <q-card-section v-else class="col-7">
              <a :href="urlItem.url">
                {{ urlItem.title }}
              </a>
            </q-card-section>
            
            <q-img
              v-if="urlItem.og_image === null"
              class="col-5 cursor-pointer"
              src="https://i.imgur.com/iYwIYZy.png"
              :ratio="1"
              @click="clickImg"
            />
            <q-img
              v-else 
              class="col-5 cursor-pointer"
              :src="urlItem.og_image"
              :ratio="1"
              @click="clickImg(urlItem, e)"
             />
          </q-card-section>
        </q-card>
      </div>

    </div>

  </main>
</template>

<script>
// import AutoComplete from 'src/components/AutoComplete.vue'
import { useStore } from 'vuex'
import { computed, toRaw } from "vue";
import { openURL } from 'quasar'

export default {
  // components: { AutoComplete }
  setup() {
    const $store = useStore()
    $store.dispatch('recommend/GET_RECOMMEND_URL', 20)
    
    const recommendUrls = computed({
      get: () => $store.getters['recommend/recommendUrls']
    })

    const clickImg = (url, e) => {
      openURL(toRaw(url).url)
    }

    return {
      recommendUrls,
      clickImg
    }
  }
}
</script>

<style scoped lang="scss">
// .my-card {
//   width: 100%;
// }
</style>