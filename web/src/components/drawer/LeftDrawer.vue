<template>

      
        <q-list>

            <router-link :to="{ name: 'Recommendation'}">
              <q-item clickable v-ripple>
                <q-item-section avatar>
                  <q-icon name="home" />
                </q-item-section>
                <q-item-section>
                  추천 페이지
                </q-item-section>
              </q-item>
            </router-link>

            <q-separator/>

            <router-link :to="{ name: 'AllUrls'}">
              <q-item clickable v-ripple>
                <q-item-section avatar>
                  <q-icon name="home" />
                </q-item-section>
                <q-item-section>
                  모든 Urls
                </q-item-section>
              </q-item>
            </router-link>

            <q-separator/>

            <q-item>
              <q-item-section>개인폴더</q-item-section>
            </q-item>              

            <template v-for="folderItem in folders" :key="folderItem.folder_id">
              <router-link :to="{ name: 'MyFolder', params: { folder_id: folderItem.folder_id }}">
                <q-item clickable v-ripple v-if="!folderItem.shared">
                  <q-item-section avatar>
                    <q-icon name="folder" />
                  </q-item-section>
                  <q-item-section>
                    {{ folderItem.folder_name}}
                  </q-item-section>
                </q-item>
              </router-link>
              
            </template>

            <q-item clickable v-ripple>
              <q-item-section avatar>
                <q-icon name="add" />
              </q-item-section>
              <q-item-section>
                추가하기
              </q-item-section>
            </q-item>

            <q-separator/>

            <q-item>
              <q-item-section>공용 폴더</q-item-section>
            </q-item>

            <template v-for="folderItem in folders" :key="folderItem.folder_id">
              <router-link :to="{ name: 'OurFolder', params: { folder_id: folderItem.folder_id }}">
                <q-item clickable v-ripple v-if="folderItem.shared">
                  <q-item-section avatar>
                    <q-icon name="folder" />
                  </q-item-section>
                  <q-item-section>
                    {{ folderItem.folder_name}}
                  </q-item-section>
                </q-item>
              </router-link>
            </template>

            <q-item clickable v-ripple>
              <q-item-section avatar>
                <q-icon name="add" />
              </q-item-section>
              <q-item-section>
                추가하기
              </q-item-section>
            </q-item>

        </q-list>


  
</template>

<script>
import { computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  setup() {
    const $store = useStore()
    const $router = useRouter()

    $store.dispatch('urls/GET_FOLDER')
    const folders = computed({
      get: () => $store.state.urls.folders
     })

    // const drawer = props.drawer

    return {
      folders,
      // drawer
    }
  }

}
</script>

<style>

</style>