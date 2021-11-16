<template>
  <main>
    <folder-header :folderData="folderData" v-if="folderId" />

    <div
      class="row q-pa-md items-start q-gutter-lg justify-center"
      v-if="!searchData"
    >
      <template
        v-for="urlItem in folderData.urls"
        :key="urlItem.memos_id"
        class="col-xs-12 col-sm-6 col-md-4"
      >
        <folder-url-card :urlItem="urlItem" />
      </template>
    </div>

    <div class="row q-pa-md items-start q-gutter-lg justify-center" v-else>
      {{ searchData }}
      <template
        v-for="urlItem in searchData.urls"
        :key="urlItem.memos_id"
        class="col-xs-12 col-sm-6 col-md-4"
      >
        <folder-url-card :urlItem="urlItem" />
      </template>
    </div>
  </main>
</template>

<script>
import { computed, watch } from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";
import FolderUrlCard from "src/components/cards/FolderUrlCard.vue";
import FolderHeader from "src/components/FolderHeader.vue";

export default {
  components: { FolderUrlCard, FolderHeader },
  setup() {
    const searchData = computed({
      get: () => $store.state.urls.searchData
    });
    const $route = useRoute();
    const $store = useStore();

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

    return {
      folderId,
      folderData,
      searchData
    };
  }
};
</script>

<style></style>
