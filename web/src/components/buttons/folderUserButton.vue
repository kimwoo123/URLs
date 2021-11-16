<template>
  <div>
    <q-btn 
      flat 
      icon="person_add_alt_1"
      @click="isOpen = true"
    />

    
    <q-dialog v-model="isOpen">
      <q-card>
        <q-card-actions align="between">
          <span>사용자 관리</span>
          <q-btn flat icon="close" color="grey" v-close-popup />
        </q-card-actions>

        <q-separator/>

        <q-card-section>
          <span class="q-ml-sm">같이 폴더를 사용할 사용자의 이메일을 적어주세요.</span>
          <q-form class="row q-gutter-md">
            <q-input>
            </q-input>
              <q-select
                borderless
                color="primary" 
                v-model="selectedOption" 
                :options="options"
              />
            <q-btn label="초대" type="submit" color="primary"/>
          </q-form>
        </q-card-section>

        <q-card-section>
          <div>user 리스트</div>
        </q-card-section>

        <!-- <q-card-section class="row items-center">
          <q-avatar icon="people" color="primary" text-color="white" size="24px"/>
          <span class="q-ml-sm">같이 폴더를 사용할 사용자의 이메일을 적어주세요.</span>
        </q-card-section>

        <q-card-actions align="right">
          <q-btn flat label="취소" color="primary" v-close-popup />
          <q-btn flat label="삭제하기" color="primary" v-close-popup @click="AddUser"/>
        </q-card-actions> -->
      </q-card>
    </q-dialog>

  </div>
</template>

<script>
import { ref, computed } from 'vue'
import { useQuasar } from 'quasar'
import { useStore } from 'vuex'

export default {
  props: ['folderData'],
  setup(props) {
    const $store = useStore()
    const isOpen = ref(false)

    const email = ref('')
    const selectedOption = ref({label: '읽기', value: '0'})
    const options = [
      {
        label: '읽기',
        value: '0',
      },
      {
        label: '쓰기',
        value: '1'
      }
    ]

    const AddUser = () => {
      const folderUserData = {
        folder_id: props.folderData._id,
        email: email.value,
        permission: '0'
      }
    }
    return {
      isOpen,
      email,
      selectedOption,
      options,
      
      AddUser
    }
  }

}
</script>

<style>

</style>