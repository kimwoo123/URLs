<template>
  <main>
    <q-card flat bordered style="width:300px">
      <q-item >
        <q-item-section avatar class="q-pa-xs">
          <q-avatar size="md">
            <img :src="urlItem.added_by.avatar" alt="사용자 프로필 사진"/>
          </q-avatar>
        </q-item-section>

        <q-item-section>
          <q-item-label>{{ urlItem.added_by.nickname }}</q-item-label>
        </q-item-section>

        <q-item-section side>
          <div>
            <q-btn
              size="12px"
              flat
              round
              color="grey"
              icon="sticky_note_2"
              @click="toggleMemo"
            />
            <span class="memo-count">{{ urlItem.memos_count }}</span>
            <q-btn
              v-if="myPermission !== 0 && willDeleted"
              size="12px"
              flat
              round
              color="grey"
              icon="bookmark_border"
              @click="openDelete"
            />
            <q-btn
              v-if="myPermission !== 0 && !willDeleted"
              size="12px"
              flat
              round
              color="grey"
              icon="bookmark"
              @click="openDelete"
            />
          </div>
        </q-item-section>
      </q-item>

      <q-separator />

      <img :src="urlItem.thumbnail ? urlItem.thumbnail : tmp_url" class="img" />

      <q-card-section>
        <div class="title">{{ urlItem.title ? urlItem.title : tmp_title }}</div>
        <div class="tags">
          <span v-for="(tag, index) in urlItem.tags" :key="index" class="tag">
            <span>{{ "#" + tag }}</span>
            <q-popup-proxy :offset="[0, 5]">
              <q-btn @click="deleteTag(tag, index)" flat size="sm" style="color: #FD6A7F;">Delete</q-btn>
            </q-popup-proxy>
          </span>
          <q-btn class="tag-add" @click="tagAddMode = true">+ 태그 추가</q-btn>
        </div>
        <q-separator class="q-mt-md q-mb-sm"/>
        <div class="url">
          <a :href="urlItem.url">{{ urlItem.url }}</a>
        </div>
      </q-card-section>
    </q-card>

    <q-dialog v-model="tagAddMode" persistent>
      <q-card style="min-width: 250px">
        <q-card-section>
          <div class="text-h6">태그 추가</div>
        </q-card-section>

        <q-card-section class="q-pt-none">
          <q-input dense v-model="newTags" autofocus @keyup.enter="tagAddMode = false" />
        </q-card-section>

        <q-card-actions align="right" class="text-primary">
          <q-btn flat label="취소" v-close-popup />
          <q-btn @click="addTag(newTags)" flat label="추가" v-close-popup />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </main>

  <!-- <q-dialog v-model="isOpen">
    <q-card>
      <q-card-section class="row items-center">
        <q-avatar icon="signal_wifi_off" color="primary" text-color="white" />
        <span class="q-ml-sm">URL을 삭제하시면 되돌릴 수 없어요!</span>
      </q-card-section>

      <q-card-actions align="right">
        <q-btn flat label="취소" color="grey" v-close-popup />
        <q-btn flat label="URL 삭제" color="primary" v-close-popup />
      </q-card-actions>
    </q-card>
  </q-dialog> -->
</template>

<script>
import { ref, computed, watch, onUnmounted } from 'vue'
import { useStore } from "vuex";
import { useQuasar } from "quasar";

export default {
  props: ["urlItem"],
  setup(props) {
    const $store = useStore();
    const $q = useQuasar();
    const willDeleted = ref(false)

    const myPermission = computed({
      get: () => $store.getters['urls/permissionNow']
    })

    const tmp_url = "https://i.imgur.com/iYwIYZy.png";
    const tmp_title = "제목 없음";

    const avatarUrl = $store.state.user.avatar;
    const storeMemoOpen = ref($store.state.urls.urlMemoOpen);

    const toggleMemo = () => {
      $store.dispatch("urls/GET_URL_MEMO", props.urlItem.memos_id);
      $store.dispatch("urls/OPEN_MEMO");
    };

    const folderId = computed({
      get: () => {
        if($store.getters['urls/folderNow']._id === '') {
          return props.urlItem.folder_id
        } else {
          return $store.getters['urls/folderNow']._id
        }
      }
    })

    let timer = null
    const StartTimeOut = () => {
        timer = setTimeout(() => {
            console.log('타이머로 영구삭제!')
            $store.dispatch('urls/DELETE_URL_BY_TIMER', urlData)
          }, 10000)
    }
    const deleteTimeOut = () => {
      clearTimeout(timer)
      console.log('삭제취소')
      $store.dispatch('urls/DELETE_WILL_DELETE_URL', urlData)
    }

    const urlData = {
      folder_id: folderId.value,
      url: props.urlItem.url,
      memos_id: props.urlItem.memos_id
    }

    const openDelete = () => {
      willDeleted.value = !willDeleted.value
      if (willDeleted.value === true) {
        $q.notify({
          color: "primary",
          message: "곧 URL을 완전히 지울게요.",
          caption: '한 번 더 눌러서 취소할 수 있습니다.',
          icon: 'bookmark_border'
        });
        $store.dispatch('urls/ADD_WILL_DELETE_URL', urlData)
        StartTimeOut()
      } else {
        $q.notify({
          color: "grey",
          message: "URL을 삭제를 취소할게요.",
          icon: 'bookmark'
        });
        $store.dispatch('urls/DELETE_WILL_DELETE_URL', urlData)
        deleteTimeOut()
      }
    }
    
    const tagAddMode = ref(false)

    let newTags = ''

    const addTag = (tag) => {
      let tags = Object.values(props.urlItem.tags)
      tags.push(tag)

      const payload = {
        folderId: $store.getters['urls/folderNow']._id,
        data: {
          url: props.urlItem.url,
          tags: tags
        }
      }

      $store.dispatch("urls/PUT_URL_TAG", payload)
    }

    const deleteTag = (tag, index) => {
      let tags = Object.values(props.urlItem.tags)
      tags.splice(index, 1)
      
      const payload = {
        folderId: $store.getters['urls/folderNow']._id,
        data: {
          url: props.urlItem.url,
          tags: tags
        }
      }

      $store.dispatch("urls/PUT_URL_TAG", payload)
    }

    return {
      myPermission,
      willDeleted,
      avatarUrl,
      storeMemoOpen,
      toggleMemo,
      tmp_url,
      tmp_title,
      openDelete,
      tagAddMode,
      newTags,
      addTag,
      deleteTag,
    };
  }
}
</script>

<style scoped lang="scss">
.card-header {
  display: flex;
  justify-content: space-between;
  padding: 8px 12px 8px 20px;
}
.profile {
  display: flex;
  align-items: center;
}
.avatar {
  width: 26px;
  height: 26px;
  border-radius: 50%;
  margin-right: 8px;
}
.memo-count {
  font-size: 12px;
  margin-left: -4px;
  margin-right: 8px;
}
.img {
  width: 100%;
  height: 300px;
  object-fit: cover;
}

.title {
  font-size: 24px;
  font-weight: 700;
  padding-bottom: 12px;
}

.tags {
  display: flex;
  flex-wrap: wrap;
  gap: 4px 6px;
  font-size: 12px;

  .tag {
    padding: 1px 8px;
    border: 1px solid $lightgray;
    border-radius: 14px;
    background-color: $verylightgray;
    color: $darkgray;
    cursor: pointer;
  }

  .tag-add {
    cursor: pointer;
    padding: 1px 8px;
    border: 1px solid $lightgray;
    border-radius: 14px;
    color: $darkgray;
    font-size: 12px;
    min-height: 0;
  }

  .q-btn:before {
    box-shadow: 0 0 0 0;
  }
}

.url {
  word-break: break-all;
  color: $darkgray;
}
</style>
