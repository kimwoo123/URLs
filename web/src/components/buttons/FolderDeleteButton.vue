<template>
  <div>

    <q-btn flat>
      <q-icon name="delete" @click="alert = true"/>
    </q-btn>

    <q-dialog v-model="alert">
      <q-card>
        <q-card-section class="row items-center">
          <q-avatar icon="delete" color="negative" text-color="white" size="24px"/>
          <span class="q-ml-sm">폴더를 삭제하면 되돌릴 수 없습니다.</span>
        </q-card-section>

        <q-card-actions align="right">
          <q-btn flat label="취소" color="primary" v-close-popup />
          <q-btn flat label="삭제하기" color="primary" v-close-popup @click="deleteFolder"/>
        </q-card-actions>
      </q-card>
    </q-dialog>

  </div>
</template>

<script>
import { ref, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  props: ['folderId'],

  setup(props) {
    const $store = useStore()
    const $router = useRouter()
    
    const alert = ref(false)
    const deleteFolder = () => {
      $router.push({ name: 'AllUrls', params: { id: $store.state.user.userid }})
      $store.dispatch('urls/DELETE_FOLDER', props.folderId)
    }

    return {
      alert,
      deleteFolder
    }
  }
}
</script>

<style>

</style>