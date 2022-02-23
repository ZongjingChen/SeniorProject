/**
 * Conference: https://threejs.org/examples/webgl_loader_fbx.html
 */

import * as THREE from 'https://unpkg.com/three@0.126.1/build/three.module.js';
import { OrbitControls } from 'https://unpkg.com/three@0.126.1/examples/jsm/controls/OrbitControls.js';
import { FBXLoader } from 'https://unpkg.com/three@0.126.1/examples/jsm/loaders/FBXLoader.js';
import {SkeletonUtils} from 'https://unpkg.com/three@0.126.1/examples/jsm/utils/SkeletonUtils.js'
import Stats from 'https://unpkg.com/three@0.126.1/examples/jsm/libs/stats.module.js';
let camera, scene, renderer, mixer, stats, human;
// const animationActions = [];
const HUMAN_MODEL_PATH = '../res/models/Samba Dancing.fbx';
const ROBOT_MODEL_PATH = '../res/models/';
const clock = new THREE.Clock();
const loader = new FBXLoader();
init();

// This function is required because of asynchronously model loading.
function play() {
    animationActions[0].play();
}

function init() {
    const container = document.createElement( 'div' );
    document.body.appendChild( container );
    camera = new THREE.PerspectiveCamera( 45, window.innerWidth / window.innerHeight, 1, 2000 );
    camera.position.set( 100, 200, 300 );
    scene = new THREE.Scene();
    scene.background = new THREE.Color( 0xa0a0a0 );
    // light
    const hemiLight = new THREE.HemisphereLight( 0xffffff, 0x444444 );
    hemiLight.position.set( 0, 200, 0 );
    scene.add( hemiLight );
    // ground
    const mesh = new THREE.Mesh( new THREE.PlaneGeometry( 2000, 2000 ), new THREE.MeshPhongMaterial( { color: 0x999999, depthWrite: false } ) );
    mesh.rotation.x = - Math.PI / 2;
    mesh.receiveShadow = true;
    scene.add( mesh );
    const grid = new THREE.GridHelper( 2000, 20, 0x000000, 0x000000 );
    grid.material.opacity = 0.2;
    grid.material.transparent = true;
    scene.add( grid );

    loader.load( HUMAN_MODEL_PATH, function ( object ) {
        // load object and add to scene
        mixer = new THREE.AnimationMixer(object);
        const action = mixer.clipAction(object.animations[1]);
        action.play();
        scene.add(object);
        human = new Human(object);
        human.playAnimation(object);

    })
    // renderer
    renderer = new THREE.WebGLRenderer( { antialias: true } );
    renderer.setPixelRatio( window.devicePixelRatio );
    renderer.setSize( window.innerWidth, window.innerHeight );
    renderer.shadowMap.enabled = true;
    container.appendChild( renderer.domElement );

    // mouse control
    const controls = new OrbitControls( camera, renderer.domElement );
    controls.target.set( 0, 100, 0 );
    controls.update();
    window.addEventListener( 'resize', onWindowResize );

    function onWindowResize() {
        camera.aspect = window.innerWidth / window.innerHeight;
        camera.updateProjectionMatrix();
        renderer.setSize( window.innerWidth, window.innerHeight );
    }

    stats = new Stats();
    container.appendChild( stats.dom );
}

class Human {

    constructor(object){
        // this.loader = loader;
        // this.clock = clock;
        // this.scene = scene;
        // this.camera = camera;
        // this.renderer = renderer;
        this.object = object;
        // this.skeletonHelper = new THREE.SkeletonHelper(object.children[2]);
        this.skeleton = object.children[2].skeleton;
        this.leftArm = SkeletonUtils.getBoneByName("mixamorigLeftArm", this.skeleton);
        this.rightArm = SkeletonUtils.getBoneByName("mixamorigRightArm", this.skeleton);
    }

    playAnimation() {
        let skeleton = this.skeleton;
        let leftArm = this.leftArm;
        let rightArm = this.rightArm;
        animate();
        console.log(this.object);
        console.log(this.skeleton);
        // let skeleton = this.skeleton;
        var delta = 0;
        function animate(){
            requestAnimationFrame(animate);


            riseLeftArm(1, 1);
            riseRightArm(1, 1);

            /**
             * Compiled code should be inserted here.
             */

            delta += clock.getDelta();
            // riseLeftArm(0.5, 1);
            // delta += clock.getDelta();
            renderer.render(scene, camera);



            function riseLeftArm(radius, time){
                let fps = 60;
                if(delta <= time){
                    leftArm.rotation.x += radius / (fps * time);
                }
            }

            function riseRightArm(radius, time){
                let fps = 60;
                if(delta <= time){
                    rightArm.rotation.x += radius / (fps * time);
                }
            }

            // TODO more functions to add
        }

    }
}
