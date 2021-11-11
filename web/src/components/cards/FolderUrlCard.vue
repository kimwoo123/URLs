<template>
  <main>
    <q-card flat bordered>
      <q-item>
        <q-item-section avatar>
          <q-avatar size="24px">
            <img :src="urlItem.added_by.avatar" alt="사용자 프로필 사진">
          </q-avatar>
        </q-item-section>
          <q-item-label>
            {{ urlItem.added_by.nickname }}
          </q-item-label>
      </q-item>

      <img :src="urlItem.thumbnail">
      <q-card-section>
        <div>타이틀</div>
        <div class="text-caption text-grey">내용물</div>
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

    return {
      avatarUrl,
    }
  }
}
</script>

<style>

</style>