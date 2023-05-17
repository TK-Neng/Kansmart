import { defineStore } from 'pinia'
import { ref } from 'vue'
export const useAnnounceStore = defineStore('announcer', () => {
    const mode = ref("active")
    const category = ref()
    const page  = ref()

    return {mode , category , page}
})