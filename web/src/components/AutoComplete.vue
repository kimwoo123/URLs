<template>
  <div class="q-pa-md">
    <p>태그</p>
    <div class="q-gutter-md">
      <q-input v-model="selectTag" label="Tag 검색" :options="options" />
      <span v-for="(tag, index) in searchResult" :key="index">
        <span @Click="tagUrl(tag)">
          {{ searchResult }}
        </span>
      </span>
      <p>My URLS</p>
      <div v-for="(urlItem, index) in searchPage" :key="index">
        <div>
          <folder-url-card :urlItem="urlItem"></folder-url-card>
        </div>
      </div>
      <q-input v-model="selectUrl" />
      <button @Click="createUrl">실험</button>
    </div>
  </div>
</template>

<script>
import { useRoute } from "vue-router";
import { ref, watch } from "vue";
import FolderUrlCard from "src/components/cards/FolderUrlCard.vue";
import { useStore } from "vuex";

const stringOptions = ["검색어를 입력해주세요."];

export default {
  components: { FolderUrlCard },
  setup() {
    const $store = useStore();
    const $route = useRoute();
    const options = ref(stringOptions);
    const searchResult = ref([]);
    const searchPage = ref([]);
    const selectTag = ref("");
    const selectUrl = ref("");

    const createUrl = () => {
      let urlData = { url: selectUrl.value, folderId: $route.params.folder_id };
      let recommendData = {
        url: selectUrl.value,
        count: 5,
        folderId: $route.params.folder_id
      };
      // $store.dispatch('urls/CREATE_URL', urlData)
      $store.dispatch("recommend/RECOMMEND_TAG", recommendData);
    };

    const tagUrl = tag => {
      console.log(tag);
    };

    watch(selectTag, val => {
      if (val !== "") {
        let queryData = { searchText: val, folderId: $route.params.folder_id };
        $store.dispatch("recommend/SEARCH_TAG", queryData);
      }
    });
    return {
      searchResult,
      createUrl,
      searchPage,
      selectTag,
      selectUrl,
      options,
      tagUrl
    };
  }
};
</script>
