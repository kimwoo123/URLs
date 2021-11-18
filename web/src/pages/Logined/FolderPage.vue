<template>
  <main class="q-pa-sm">
    <folder-header :folderData="folderData" v-if="folderId" />

    <div
      class="row q-pa-md items-start q-gutter-lg justify-center"
      v-if="searchData.length === 0"
    >
      <template v-if="folderData.urls == false">
        <div class="colum">
        <div class="text-h5 text-grey">폴더가 비었어요!</div>
        </div>
        
      </template>
      <template
        v-for="urlItem in folderData.urls"
        :key="urlItem.memos_id"
        class="col-xs-12 col-sm-6 col-md-4"
      >
        <folder-url-card :urlItem="urlItem" />
      </template>
    </div>

    <div
      class="row q-pa-md items-start q-gutter-lg justify-center"
      v-else
    >
      <template v-if="searchData[0].urls == false">
        <div>검색 결과를 찾을 수 없어요!</div>
      </template>
      <template
        v-for="urlItem in searchData[0].urls"
        :key="urlItem.memos_id"
        class="col-xs-12 col-sm-6 col-md-4"
      >
        <folder-url-card :urlItem="urlItem" />
      </template>
    </div>
    <auto-complete></auto-complete>

  </main>
</template>

<script>
import { computed, watch, onUnmounted} from "vue";
import { useStore } from "vuex";
import { useRoute, onBeforeRouteLeave } from "vue-router";
import FolderUrlCard from "src/components/cards/FolderUrlCard.vue";
import FolderHeader from "src/components/FolderHeader.vue";
import AutoComplete from '../../components/AutoComplete.vue';

export default {
  components: { FolderUrlCard, FolderHeader, AutoComplete },

  setup() {
    const $route = useRoute();
    const $store = useStore();
    
    const searchData = computed({
      get: () => $store.state.urls.searchData
    });

    const folderId = computed({
      get: () => {
        $store.dispatch("urls/GET_FOLDER_ULR", $route.params.folder_id);
        $store.dispatch("urls/CLOSE_MEMO");
        return $route.params.folder_id;
      }
    });

    const folderData = computed({
      get: () => $store.getters["urls/folderNow"]
    });

    // onUnmounted(() => {
    //   const deleteList = $store.getters['urls/willDeleteURL']
    //   deleteList.forEach(element => {
    //     $store.dispatch('urls/DELETE_URL', element)
    //   })
    // })

    watch(folderId, () => {
      const deleteList = $store.getters['urls/willDeleteURL']
      deleteList.forEach(element => {
        $store.dispatch('urls/DELETE_URL', element)
      })
    })

    // onBeforeRouteLeave(() => {
    //   console.log('라우트이동!')
    //   const deleteList = $store.getters['urls/willDeleteURL']
    //   deleteList.forEach(element => {
    //     $store.dispatch('urls/DELETE_URL', element)
    //   })
    //   next()
    // })

    return {
      folderId,
      folderData,
      searchData
    };

  }

}
</script>

<style>

</style>