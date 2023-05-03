<script setup>
import { getDataById } from "../composable/getData";
import { useRoute } from "vue-router";
import { ref, onMounted, onBeforeMount } from "vue";


</script>

<template>
  <div class="min-h-screen flex flex-col">
    <div class="ml-20 mt-8">
      <h1 class="text-3xl font-bold">Announcement Detail:</h1>



      <div class="mt-8">
       <h3>TItle</h3>
        <div class=" border rounded-lg mr-4">
          <textarea class="h-8 w-full"></textarea>
        </div>
      </div>

      <div class="mt-2">
    <h3>Category</h3>
    <div class="border rounded-lg mr-4 inline-block">
      <select v-model="selectedCategory" class="h-8 w-full pl-12 pr-8">
        <option value="">-- Select category --</option>
        <option value="">-- Wow --</option>
        <option v-for="(category, index) in categories" :key="index" :value="category">{{ item.announcementCategory }}</option> 
       <!-- ^ มั่ว -->
      </select>
    </div>
  </div>

      <div class="mt-8">
       <h3>Description</h3>
        <div class=" border rounded-lg mr-4">
          <textarea class="h-40 w-full"></textarea>
        </div>
      </div>

      <div class="mt-8">
       <h3>Publish Date</h3>
        <div class=" border rounded-lg inline-block mr-4">
            <input type="date" class="h-8 w-full" placeholder="dd/mm/yyyy" />
        </div>
        <div class=" border rounded-lg inline-block mr-4">
            <input type="time" class="h-8 w-full" step="60">
        </div>
      </div>

      
      <div class="mt-8">
       <h3>Close Date</h3>
        <div class=" border rounded-lg inline-block mr-4">
            <input type="date" class="h-8 w-full" placeholder="dd/mm/yyyy" />
        </div>
        <div class="border rounded-lg inline-block mr-4">
            <input type="text" class="h-8 w-full" placeholder="hh:mm" pattern="[0-9]{2}:[0-9]{2}">
            
        </div>
      </div>

      <div class="mt-8">
       <h3>Display</h3>
       <div>
  <input type="checkbox" id="myCheckbox">
  <label class="ml-2">Check to show this announcement</label>
</div>
</div>



<div class="mt-2">

    
    <button
        class="hover:bg-green-500 font-bold py-1 px-2 rounded bg-green-300 justify-center  w-20"
        >
          Submit
        </button>
        <router-link :to="{ name: 'Main' }"
        >
        <button
        class="hover:bg-red-500 font-bold py-1 px-2 rounded bg-red-300 justify-center ml-3 w-20"
        >
          Cancle
        </button>
        </router-link>
      </div>









    </div>
  </div>

</template>

<style scoped>
.popup {
  z-index: 9999;
}
</style>
