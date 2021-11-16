<template>
  <main>
    <q-btn flat round icon="create" @click="openDialog" size="13px" />

    <q-dialog v-model="isOpen">
      <q-card style="min-width: 350px">
        <q-card-section>
          <div class="text-h6">추가할 URL을 입력해주세요.</div>
        </q-card-section>
        <q-card-section class="q-pt-none">
          <q-input v-model="urlName" dense autofocus />
          <q-input v-if="false"></q-input>
        </q-card-section>
        <q-btn flat label="태그 추천:" @click="recommendTag" />
        <span v-for="(tag, index) in recommendResult" :key="index">
          <span>#{{ tag }}&nbsp;&nbsp; </span>
        </span>
        <q-card-section>
          <div class="text-h6">함께 저장할 태그를 입력해주세요.</div>
        </q-card-section>
        <q-card-section class="q-pt-none">
          <q-input float-label="Floating Label" v-model="customTags" />
          <q-input v-if="false"></q-input>
        </q-card-section>

        <span v-if="splitTags">
          <span v-for="(tagObj, index) in splitTags" :key="index">
            <q-chip v-if="tagObj">{{ tagObj }}</q-chip>
          </span>
        </span>

        <q-card-actions align="right" class="text-primary">
          <q-btn flat label="취소" v-close-popup type="reset" />
          <q-btn flat label="만들기" @click="createUrl" type="submit" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </main>
</template>

<script>
import { ref, computed } from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";

export default {
  setup() {
    const $store = useStore();
    const $route = useRoute();
    const isOpen = ref(false);
    const urlName = ref("");
    const customTags = ref("");
    const customTagList = ref([]);
    const recommendResult = computed({
      get: () => {
        return $store.state.recommend.recommendTag;
      }
    });

    const splitTags = computed({
      get: () => {
        return new Set(customTags.value.split(/[\s\,\.]/));
      }
    });

    const recommendTag = () => {
      let recommendData = {
        url: urlName.value,
        count: 5,
        folderId: $route.params.folder_id
      };
      $store.dispatch("recommend/RECOMMEND_TAG", recommendData);
    };

    const createUrl = () => {
      splitTags.value.delete("");
      let urlData = {
        url: urlName.value,
        folderId: $route.params.folder_id,
        tags: Array.from(splitTags.value)
      };
      $store.dispatch("urls/CREATE_URL", urlData);
      isOpen.value = false;
    };

    const openDialog = () => {
      isOpen.value = true;
    };
    return {
      recommendTag,
      splitTags,
      createUrl,
      openDialog,
      recommendResult,
      customTags,
      customTagList,
      isOpen,
      urlName
    };
  }
};
</script>
