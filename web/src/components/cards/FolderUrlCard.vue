<template>
  <main>
    <q-card flat bordered style="width:300px">
      <div class="card-header">
        <div class="profile">
          <img
            :src="urlItem.added_by.avatar"
            alt="사용자 프로필 사진"
            class="avatar"
          />
          <span>{{ urlItem.added_by.nickname }}</span>
        </div>
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
        </div>
      </div>

      <img :src="urlItem.thumbnail ? urlItem.thumbnail : tmp_url" class="img" />

      <q-card-section>
        <div class="title">{{ urlItem.title ? urlItem.title : tmp_title }}</div>
        <div class="tags">
          <span v-for="(tag, index) in urlItem.tags" :key="index" class="tag">{{
            "#" + tag
          }}</span>
          <q-btn class="tag-add">+ 태그 추가</q-btn>
        </div>
        <hr style="margin-top: 16px" />
        <span class="url"
          ><a :href="urlItem.url">{{ urlItem.url }}</a></span
        >
      </q-card-section>
    </q-card>
  </main>
</template>

<script>
import { useStore } from "vuex";

export default {
  props: ["urlItem"],
  setup(props) {
    const $store = useStore();

    const tmp_url = "https://i.imgur.com/iYwIYZy.png";
    const tmp_title = "제목 없음";

    const avatarUrl = $store.state.user.avatar;
    const storeMemoOpen = $store.state.urls.urlMemoOpen;

    const toggleMemo = () => {
      $store.dispatch("urls/GET_URL_MEMO", props.urlItem.memos_id);
      $store.dispatch("urls/OPEN_MEMO");
    };

    return {
      avatarUrl,
      storeMemoOpen,
      toggleMemo,
      tmp_url,
      tmp_title
    };
  }
};
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
