<template>
  <main>
    <q-card flat bordered>
      <q-item>
        <q-item-section avatar>
          <q-avatar size="24px">
            <img :src="urlItem.added_by.avatar" alt="사용자 프로필 사진">
          </q-avatar>
        </q-item-section>
        <q-item-section>
          <q-item-label>
            {{ urlItem.added_by.nickname }}
          </q-item-label>
        </q-item-section>
        <q-item-section>
          <q-btn flat round color="grey" icon="sticky_note_2" @click="toggleMemo"/>
        </q-item-section>
      </q-item>

      <img :src="urlItem.thumbnail">
      <q-card-section>
        <div>타이틀</div>
        <span v-for="(tag, index) in urlItem.tags" :key="index">
          <span>
            {{ tag }}&nbsp;&nbsp;
          </span>
        </span>
        <div class="text-caption text-grey">내용물</div>
        <div></div>
        <div>{{ urlItem.url}}</div>
      </q-card-section>
    </q-card>
  </main>
</template>

<script>
import { useStore } from 'vuex'
import { useRoute } from 'vue-router'

export default {
  props: ['urlItem'],
  setup(props) {
    const $route = useRoute()
    const $store = useStore()

    const avatarUrl = $store.state.user.avatar
    const storeMemoOpen = $store.state.urls.urlMemoOpen
  
    const toggleMemo = () => {
      $store.dispatch('urls/GET_URL_MEMO', props.urlItem.memos_id)
      $store.dispatch('urls/OPEN_MEMO')
    }

    return {
      avatarUrl,
      storeMemoOpen,
      toggleMemo,
    }
  }
}
</script>

<style>

</style>